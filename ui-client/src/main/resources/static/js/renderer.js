/**
 * 歌单 / 歌手卡片列表渲染，替代三个页面各自复制的一份 createElement 逻辑。
 *
 * @param containerSelector 容器选择器，如 '#songListDiv'
 * @param list              数据数组
 * @param options           { imgPrefix, imgField, onClick }
 *                          imgPrefix 图片目录前缀，imgField 图片文件名字段，onClick(item) 点击回调
 */
function renderCardGrid(containerSelector, list, options) {
    var container = document.querySelector(containerSelector);
    if (!container) {
        return;
    }
    container.innerHTML = '';
    if (!list || list.length === 0) {
        return;
    }

    var opts = options || {};
    // 与 index.css 里 .layui-col-md3 { width: 20% } 保持一致：每行 5 个正好铺满
    var perRow = opts.perRow || 5;
    var row = null;

    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (i % perRow === 0) {
            row = document.createElement('div');
            row.className = 'layui-row layui-col-space15';
            container.appendChild(row);
        }

        var col = document.createElement('div');
        col.className = 'layui-col-md3';

        var panel = document.createElement('div');
        panel.className = 'layui-panel';

        var coverLink = document.createElement('a');
        coverLink.href = 'javascript:;';
        coverLink.title = item.name;
        coverLink.onclick = (function (data) {
            return function () {
                if (typeof opts.onClick === 'function') {
                    opts.onClick(data);
                }
            };
        })(item);

        var cover = document.createElement('img');
        cover.className = 'cover';
        cover.src = (opts.imgPrefix || '') + item[opts.imgField || 'coverFileName'];
        coverLink.appendChild(cover);

        var titleDiv = document.createElement('div');
        titleDiv.className = 'subheadDiv';

        var titleLink = document.createElement('a');
        titleLink.href = 'javascript:;';
        titleLink.title = item.name;

        var title = document.createElement('strong');
        title.className = 'subhead';
        title.innerText = item.name;
        titleLink.appendChild(title);
        titleDiv.appendChild(titleLink);

        panel.appendChild(coverLink);
        panel.appendChild(titleDiv);
        col.appendChild(panel);
        row.appendChild(col);
    }
}
