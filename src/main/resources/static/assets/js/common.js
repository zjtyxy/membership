var Common = function () {
    var initTable = function (ele, url, cols, table, doneCallBack) {
        return table.render({
            elem: ele
            , url: url
            , method: 'POST'
            , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , cols: cols
            , page: {
                limits: [10, 20, 50, 100]
            },
            request: {
                pageName: 'current',
                limitName: 'size'
            },
            done: function (res, curr, count) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    };

    var searchTable = function (formId, tableIns) {
        var queryParams = getParams(formId);
        tableIns.reload({
            where: {condition: queryParams},
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };

    var getParams = function (formId) {
        var $ = layui.jquery;
        var _params = {};
        $.each($('#' + formId).serializeArray(), function (i, field) {
            if (null != field.value && "" != field.value) {
                _params[field.name] = field.value;
            }
        });
        return _params;
    };

    var upload = function (eleId, layUpload, done, error, accept, exts) {
        layUpload.render({
            elem: eleId //绑定元素
            , url: '/upload/' //上传接口
            , accept: accept === undefined ? 'file' : accept
            , exts: exts === undefined ? 'jpg|png|gif|bmp|jpeg' : exts
            , done: function (res) {
                //上传完毕回调
                if (typeof (done) === 'function') {
                    done(res)
                }
            }
            , error: function () {
                //请求异常回调
                if (typeof (error) === 'function') {
                    error()
                }
            }
        });
    };

    var openFrame = function (url, title, width, height) {
        width = width === undefined ? '900px' : width;
        height = height === undefined ? '500px' : height;
        return top.layer.open({
            area: [width, height],
            type: 2,
            title: title,
            content: url //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    };
    return {
        initTable: function (ele, url, cols, table, doneCallBack) {
            return initTable(ele, url, cols, table, doneCallBack);
        },
        searchTable: function (formId, table) {
            searchTable(formId, table);
        },
        uploadFile: function (eleId, layUpload, done, error, accept, exts) {
            upload(eleId, layUpload, done, error, accept, exts);
        },
        openFrame: function (url, title, width, height) {
            return openFrame(url, title, width, height);
        }
    }
}();