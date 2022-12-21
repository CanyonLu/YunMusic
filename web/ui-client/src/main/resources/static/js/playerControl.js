var myAduio = document.getElementsByTagName('audio')[0];
var progressTime = document.getElementsByClassName('time')[0];
var nowLine = 0;
var rollT;
var nextPlaySong;
var playState = false;
var timeArray1 = new Array();
var timeInterval = new Array();

window.onload = function () {
    setMouseEvent();
    // setTimeText();
};

// 设置事件
function setMouseEvent() {
    // 音量控制
    let volume_now = document.getElementsByClassName('volume-now')[0];
    let volume_back = document.getElementsByClassName('volume-back')[0];
    let volume_text = document.getElementsByClassName('volume-text')[0];
    let volume_a = document.getElementsByClassName('volume')[0];
    volume_back.onmousedown = function (e) {
        volume_now.style.width = e.offsetX + 'px';
        myAduio.volume = e.offsetX / 100;
        volume_text.innerText = volume_now.clientWidth;
        volume_back.onmousemove = function (ev) {
            let volume = ev.offsetX;
            if (volume > 100) {
                volume = 100;
            }
            volume_now.style.width = volume + 'px';
            myAduio.volume = volume / 100;
            volume_text.innerText = volume_now.clientWidth;
        };
        document.onmouseup = function () {
            if (myAduio.volume == 0) {
                volume_a.firstChild.classList.value = "layui-icon layui-icon-mute";
                // volume_a.style.backgroundImage = 'url(../img/mute.png)';
            } else {
                volume_a.firstChild.classList.value = "layui-icon layui-icon-speaker";
                // volume_a.style.backgroundImage = 'url(../img/volume.png)';
            }
            volume_back.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    };

    // 进度控制
    let progress_now = document.getElementsByClassName('progress-now')[0];
    let progress_bar = document.getElementsByClassName('progress-bar')[0];
    progress_bar.onmousedown = function (e) {
        progress_now.style.width = e.offsetX + 'px';
        myAduio.pause();
        myAduio.currentTime =
            (e.offsetX * myAduio.duration) / progress_bar.clientWidth;
        setTimeText();
        progress_bar.onmousemove = function (ev) {
            let progress = ev.offsetX;
            if (progress > progress_bar.clientWidth) {
                progress = progress_bar.clientWidth;
            }
            progress_now.style.width = progress + 'px';
            myAduio.currentTime =
                (progress * myAduio.duration) / progress_bar.clientWidth;
            setTimeText();
        };
        document.onmouseup = function () {
            myAduio.play();
            setPlay(true);
            // for (var i = 0; i < timeArray1.length; i++) {
            //     if (myAduio.currentTime < timeArray1[i]) {
            //         nowLine = i - 1;
            //         // setLyrics(i - 2);
            //         timeInterval[nowLine] =
            //             timeArray1[nowLine + 1] - myAduio.currentTime;
            //         setPlay(true);
            //         break;
            //     }
            // }
            progress_bar.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    };
}

// 设置播放状态
function setPlay(state) {
    //歌曲src为空说明进入主页后直接点击播放键，此时先判断localstorage的歌单里有无歌曲，有就播放，没有不播放
    if(!myAduio.src){
        let playlist = localStorage.getItem("playlist");
        let list = JSON.parse(playlist);
        if(list){
            myAduio.src = realm_dev + client + "playlist/getSongData?id=" + list[0].id;
            let data = {
                id: list[0].id,
                name: list[0].name,
                singer: list[0].singer
            };
            localStorage.setItem("playingMusicData", JSON.stringify(data));
        }else {
            return false;
        }
    }
    //
    var play_pause = document.getElementsByClassName('play-pause')[0];
    if (state == null) {
        state = myAduio.paused;
    }
    clearTimeout(rollT);
    if (state == true) {
        myAduio.play();
        play_pause.firstChild.classList.value = "layui-icon layui-icon-pause";
        let item = localStorage.getItem("playingMusicData");
        let data = JSON.parse(item);
        $("#titleText").html("当前播放： " + data.singer + " - " + data.name);
        playState = true;
        getPlaylistNextSong(data.id);
        setTimeText();
        setProgress();
        // checkEnd();
        // 开始播放后要重新将时间间距改回来，不然下次播放计时器会出错
        timeInterval[nowLine] = timeArray1[nowLine + 1] - timeArray1[nowLine];
    } else {
        myAduio.pause();
        play_pause.firstChild.classList.value = "layui-icon layui-icon-play";
        $("#titleText").html("");
        // play_pause.style.backgroundImage = 'url(../img/play.png)';
        playState = false;
        timeInterval[nowLine] = timeArray1[nowLine + 1] - myAduio.currentTime;
    }
}

// 设置音量
function setVolume(volume) {
    myAduio.volume = volume;
}

// 设置静音
function setMuted() {
    let volume_now = document.getElementsByClassName('volume-now')[0];
    let volume_text = document.getElementsByClassName('volume-text')[0];
    let volume_a = document.getElementsByClassName('volume')[0];
    if (myAduio.muted == true) {
        myAduio.muted = false;
        volume_a.firstChild.classList.value = "layui-icon layui-icon-speaker";
        // volume_a.style.backgroundImage = 'url(../img/volume.png)';
        volume_now.style.width = myAduio.volume * 100 + 'px';
        volume_text.innerText = myAduio.volume * 100;
    } else {
        myAduio.muted = true;
        volume_a.firstChild.classList.value = "layui-icon layui-icon-mute";
        // volume_a.style.backgroundImage = 'url(../img/mute.png)';
        volume_now.style.width = '0';
        volume_text.innerText = '0';
    }
}

//获取并播放上一首
function playPreviousSong() {
    let item = localStorage.getItem("playingMusicData");
    let data = JSON.parse(item);
    let playlist = localStorage.getItem("playlist");
    let list = JSON.parse(playlist);
    let previousSong;
    if(list && list.length > 0){
        let playingSerialNumber = 0;
        list.forEach(item => {
            if(item.id == data.id){
                playingSerialNumber = item.serialNumber;
            }
        });
        let previousPlaySerialNumber = playingSerialNumber === 1? list.length : playingSerialNumber - 1;
        list.forEach(item =>{
            if(item.serialNumber == previousPlaySerialNumber){
                previousSong = item;
            }
        });
    }
    if(previousSong){
        let data = {
            id: previousSong.id,
            name: previousSong.name,
            singer: previousSong.singer
        };
        localStorage.setItem("playingMusicData", JSON.stringify(data));
        myAduio.src = realm_dev + client + "playlist/getSongData?id=" + previousSong.id;
        setPlay();
    }
}

//播放下一首
function playNextSong() {
    if(!nextPlaySong){
        getPlaylistNextSong();
    }
    if(nextPlaySong){
        let data = {
            id: nextPlaySong.id,
            name: nextPlaySong.name,
            singer: nextPlaySong.singer
        };
        localStorage.setItem("playingMusicData", JSON.stringify(data));
        myAduio.src = realm_dev + client + "playlist/getSongData?id=" + nextPlaySong.id;
        setPlay();
    }
}

// 获取播放列表的下一首歌曲
function getPlaylistNextSong(id) {
    if(!id){
        let item = localStorage.getItem("playingMusicData");
        let data = JSON.parse(item);
        id = data.id;
    }
    let playlist = localStorage.getItem("playlist");
    let list = JSON.parse(playlist);
    if(list && list.length > 1){
        let playingSerialNumber = 0;
        list.forEach(item =>{
            if(item.id == id){
                playingSerialNumber = item.serialNumber;
            }
        });
        let nextPlaySerialNumber = list.length < playingSerialNumber + 1 ? 1 : playingSerialNumber + 1;
        list.forEach(item =>{
            if(item.serialNumber == nextPlaySerialNumber){
                nextPlaySong = item;
            }
        });
    }
}

//判断是否结束，并自动播放下一首
myAduio.addEventListener('ended', function () {
    if(nextPlaySong){
        let data = {
            id: nextPlaySong.id,
            name: nextPlaySong.name,
            singer: nextPlaySong.singer
        };
        localStorage.setItem("playingMusicData", JSON.stringify(data));
        myAduio.src = realm_dev + client + "playlist/getSongData?id=" + nextPlaySong.id;
        setPlay();
    }else {
        document.getElementsByClassName('play-pause')[0].firstChild.classList.value = "layui-icon layui-icon-play";
        $("#titleText").html("");
    }
}, false);

// 设置进度文本
function setTimeText() {
    var nowTime = myAduio.currentTime;
    var allTime = myAduio.duration;
    // 计算时间，若为个位数，补0
    if (Math.floor(nowTime % 60) < 10) {
        nowTime = Math.floor(nowTime / 60) + ':0' + Math.floor(nowTime % 60);
    } else {
        nowTime = Math.floor(nowTime / 60) + ':' + Math.floor(nowTime % 60);
    }
    if (Math.floor(allTime % 60) < 10) {
        allTime = Math.floor(allTime / 60) + ':0' + Math.floor(allTime % 60);
    } else {
        allTime = Math.floor(allTime / 60) + ':' + Math.floor(allTime % 60);
    }
    progressTime.innerText = nowTime + '/' + allTime;
    // 每0.1秒执行一次
    if (myAduio.paused == false) {
        setTimeout(setTimeText, 100);
    }
}

// 设置进度条进度
function setProgress() {
    let progress_now = document.getElementsByClassName('progress-now')[0];
    let progress_bar = document.getElementsByClassName('progress-bar')[0];
    let progress = Math.floor(
        (myAduio.currentTime / myAduio.duration) * progress_bar.clientWidth
    );
    progress_now.style.width = progress + 'px';
    if (myAduio.paused == false) {
        setTimeout(setProgress, 100);
    }
}

// 获取网页属性
function getDocument(attribute) {
    if (attribute == 'sT') {
        return document.documentElement.scrollTop;
    } else if (attribute == 'sL') {
        return document.documentElement.scrollLeft;
    } else if (attribute == 'sH') {
        return document.documentElement.scrollHeight;
    } else if (attribute == 'sW') {
        return document.documentElement.scrollWidth;
    } else if (attribute == 'cH') {
        return document.documentElement.clientHeight;
    } else if (attribute == 'cW') {
        return document.documentElement.clientWidth;
    }
}