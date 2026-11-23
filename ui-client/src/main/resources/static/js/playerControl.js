var myAudio = document.getElementsByTagName('audio')[0];
var progressTime = document.getElementsByClassName('time')[0];
var nextPlaySong;
var playState = false;

window.onload = function () {
    setMouseEvent();
};

/* ---------------- 交互事件 ---------------- */

function setMouseEvent() {
    bindVolumeDrag();
    bindProgressDrag();
}

// 音量控制
function bindVolumeDrag() {
    var volumeNow = document.getElementsByClassName('volume-now')[0];
    var volumeBack = document.getElementsByClassName('volume-back')[0];
    var volumeText = document.getElementsByClassName('volume-text')[0];
    var volumeBtn = document.getElementsByClassName('volume')[0];

    volumeBack.onmousedown = function (e) {
        setVolumeByOffset(e.offsetX);
        volumeBack.onmousemove = function (ev) {
            setVolumeByOffset(ev.offsetX);
        };
        document.onmouseup = function () {
            volumeBtn.firstChild.classList.value = myAudio.volume === 0
                ? 'layui-icon layui-icon-mute'
                : 'layui-icon layui-icon-speaker';
            volumeBack.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    };

    function setVolumeByOffset(offset) {
        var volume = Math.max(0, Math.min(offset, 100));
        volumeNow.style.width = volume + 'px';
        myAudio.volume = volume / 100;
        volumeText.innerText = volume;
    }
}

// 进度控制
function bindProgressDrag() {
    var progressNow = document.getElementsByClassName('progress-now')[0];
    var progressBar = document.getElementsByClassName('progress-bar')[0];

    progressBar.onmousedown = function (e) {
        progressNow.style.width = e.offsetX + 'px';
        myAudio.pause();
        myAudio.currentTime = (e.offsetX * myAudio.duration) / progressBar.clientWidth;
        setTimeText();

        progressBar.onmousemove = function (ev) {
            var progress = Math.min(ev.offsetX, progressBar.clientWidth);
            progressNow.style.width = progress + 'px';
            myAudio.currentTime = (progress * myAudio.duration) / progressBar.clientWidth;
            setTimeText();
        };
        document.onmouseup = function () {
            myAudio.play();
            setPlay(true);
            progressBar.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    };
}

/* ---------------- 播放控制 ---------------- */

// 设置播放状态：不传参为切换播放/暂停
function setPlay(state) {
    if (!myAudio.src) {
        return false;
    }
    var playPause = document.getElementsByClassName('play-pause')[0];
    if (state == null) {
        state = myAudio.paused;
    }

    if (state) {
        myAudio.play();
        playPause.firstChild.classList.value = 'layui-icon layui-icon-pause';
        var data = currentSong();
        if (data) {
            $('#titleText').html('当前播放： ' + data.singer + ' - ' + data.name);
        }
        playState = true;
        getPlaylistNextSong(data ? data.id : null);
        setTimeText();
        setProgress();
    } else {
        myAudio.pause();
        playPause.firstChild.classList.value = 'layui-icon layui-icon-play';
        $('#titleText').html('');
        playState = false;
    }
}

// 静音开关
function setMuted() {
    var volumeNow = document.getElementsByClassName('volume-now')[0];
    var volumeText = document.getElementsByClassName('volume-text')[0];
    var volumeBtn = document.getElementsByClassName('volume')[0];

    if (myAudio.muted) {
        myAudio.muted = false;
        volumeBtn.firstChild.classList.value = 'layui-icon layui-icon-speaker';
        volumeNow.style.width = myAudio.volume * 100 + 'px';
        volumeText.innerText = myAudio.volume * 100;
    } else {
        myAudio.muted = true;
        volumeBtn.firstChild.classList.value = 'layui-icon layui-icon-mute';
        volumeNow.style.width = '0';
        volumeText.innerText = '0';
    }
}

// 播放上一首
function playPreviousSong() {
    var data = currentSong();
    var list = playlist();
    var previousSong = null;
    if (data && list && list.length > 0) {
        var playingNumber = findSerialNumber(list, data.id);
        var previousNumber = playingNumber <= 1 ? list.length : playingNumber - 1;
        previousSong = findBySerialNumber(list, previousNumber);
    }
    if (previousSong) {
        playSong(previousSong);
    }
}

// 播放下一首
function playNextSong() {
    nextPlaySong = null;
    getPlaylistNextSong();
    if (nextPlaySong) {
        playSong(nextPlaySong);
    }
}

// 计算下一首
function getPlaylistNextSong(id) {
    if (!id) {
        var data = currentSong();
        id = data ? data.id : null;
    }
    var list = playlist();
    if (!id || !list || list.length <= 1) {
        return;
    }
    var playingNumber = findSerialNumber(list, id);
    var nextNumber = playingNumber >= list.length ? 1 : playingNumber + 1;
    nextPlaySong = findBySerialNumber(list, nextNumber);
}

// 播放一首歌：写入当前播放信息并切换音源
function playSong(song) {
    var data = {
        id: song.id,
        name: song.name,
        singer: song.singer
    };
    localStorage.setItem('playingMusicData', JSON.stringify(data));
    myAudio.src = API + 'playlist/getSongData?id=' + song.id;
    setPlay();
}

// 自动连播
myAudio.addEventListener('ended', function () {
    if (nextPlaySong) {
        playSong(nextPlaySong);
    } else {
        document.getElementsByClassName('play-pause')[0].firstChild.classList.value = 'layui-icon layui-icon-play';
        $('#titleText').html('');
    }
}, false);

/* ---------------- 播放列表存取 ---------------- */

function playlist() {
    var item = localStorage.getItem('playlist');
    return item ? JSON.parse(item) : null;
}

function currentSong() {
    var item = localStorage.getItem('playingMusicData');
    return item ? JSON.parse(item) : null;
}

function findSerialNumber(list, id) {
    var serialNumber = 0;
    list.forEach(function (item) {
        if (item.id == id) {
            serialNumber = item.serialNumber;
        }
    });
    return serialNumber;
}

function findBySerialNumber(list, serialNumber) {
    var found = null;
    list.forEach(function (item) {
        if (item.serialNumber == serialNumber) {
            found = item;
        }
    });
    return found;
}

/* ---------------- 时间与进度 ---------------- */

function setTimeText() {
    progressTime.innerText = formatTime(myAudio.currentTime) + '/' + formatTime(myAudio.duration);
    if (!myAudio.paused) {
        setTimeout(setTimeText, 100);
    }
}

function setProgress() {
    var progressNow = document.getElementsByClassName('progress-now')[0];
    var progressBar = document.getElementsByClassName('progress-bar')[0];
    if (myAudio.duration > 0) {
        progressNow.style.width = Math.floor((myAudio.currentTime / myAudio.duration) * progressBar.clientWidth) + 'px';
    }
    if (!myAudio.paused) {
        setTimeout(setProgress, 100);
    }
}

function formatTime(seconds) {
    if (!seconds || isNaN(seconds)) {
        return '0:00';
    }
    var minute = Math.floor(seconds / 60);
    var second = Math.floor(seconds % 60);
    return minute + ':' + (second < 10 ? '0' + second : second);
}
