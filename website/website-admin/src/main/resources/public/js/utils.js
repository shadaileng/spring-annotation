var TableUtils = function () {
    let defaultOptions = {
        mode: 'local',          // local | server
        url: '',                // server mode
        data: [],               //
        head: [],
        columns: [],            // 列
        target: '#tableEL',     // 初始化检查target
        status: false,          // 插件状态
        isBoot: false           // 是否引入Bootstrap
    }
    let _extends = function (src, target) {
        for (let i in src) {
            let val = src[i]
            target[i] = val
        }
        return target
    }
    let init = function (opts) {
        opts = _extends(opts, defaultOptions)
        console.log(opts)
        if (document.querySelector(opts.target) == null) {
            console.log('未指定目标元素|#tableEL')
            return
        }
        if (opts.columns.length == 0 && opts.data.length > 0) {
            console.log('[WARN] 列名未定义')
            opts.columns = Object.keys(opts.data[0])
            let _head = document.querySelector(opts.target + ' thead tr')
            if (opts.head.length <= 0) {
                let _row = document.createElement('tr')
                opts.columns.forEach((e)=>{
                    let _col = document.createElement('th')
                    _col.innerHTML = e
                    _row.appendChild(_col)
                })
                _head.replaceWith(_row)
            } else {

            }
            document.querySelectorAll(opts.target + ' thead tr th').forEach((e, i) => {
                e.innerHTML = opts.columns[i]
            })
        } else {
            document.querySelectorAll(opts.target + ' thead tr th').length != opts.columns.length && console.log('[WARN] 定义列数与表格列数不符')
        }

        typeof($.fn.popover) == 'undefined' ? console.log('[WARN] Bootstrap未加载...') : opts.isBoot = true
        opts.isBoot ? document.querySelector(opts.target).className = 'table table-bordered table-hover' : ''
        if (opts.mode == 'local') {
            setTableData(opts.data, opts.target)
        } else {
            if (opt.url == '') {
                console.log('url为空')
                setTableData(opts.data, opts.target)
            } else {

            }
        }
    }
    let setTableData = function (data, _tabSelector_) {
        data.length > 0 || console.log('data为空')
        let selector = _tabSelector_ + ' tbody'
        let _body = document.querySelector(selector)
        let _columns = defaultOptions.columns
        data.forEach((e, i) => {
            let _row = document.createElement('tr')
            _columns.forEach((el, i)=>{
                let _col = document.createElement('td')
                let _innerEl = e[el]
                let _content = typeof _innerEl == 'function' ? _innerEl() :( _innerEl == undefined ? '' :  _innerEl.toString())
                _col.innerHTML = _content
                _row.appendChild(_col)
            })
            _body.appendChild(_row)
        })
    }
    return {
        init: function (tableSelector) {
            init(tableSelector)
        },
        setTableData: function (data, _tabSelector_) {
            setTableData(data, _tabSelector_)
        }
    }
}()
var AJAX = function () {
    let _opts = {
        url: '',
        type: 'GET',
        data: {},
        async: true,
        beforeSend: function () {
            console.log('beforeSend...')
        },
        success: function (result) {
            console.log('success: ' + result)
        },
        error: function (error) {
            console.log('error: ' + error)
        }
    }
    let open = function (opts) {
        for (let key in opts) {
            let val = opts[key]
            _opts[key] = val
        }
        if (_opts.url == '') {
            console.log('url为空')
            return
        }
        let xhr = new XMLHttpRequest();
        xhr.open(_opts.type, _opts.url, _opts.asy)
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 1){ // 1: 服务器连接已建立（open方法已经被调用）
                console.log('1: 服务器连接已建立（open方法已经被调用）')
            } else if(xhr.readyState == 2) { // 2: 请求已接收（send方法已经被调用，并且头部和状态已经可获得）
                console.log('2: 请求已接收（send方法已经被调用，并且头部和状态已经可获得）')
            } else if(xhr.readyState == 3) { // 3: 请求处理中（下载中，responseText 属性已经包含部分数据）
                console.log('3: 请求处理中（下载中，responseText 属性已经包含部分数据）')
            } else if(xhr.readyState == 4) { // 4: 请求已完成，且响应已就绪（下载操作已完成）
                console.log(' 4: 请求已完成，且响应已就绪（下载操作已完成）')
                if (xhr.status == 200 || xhr.status == 304) {
                    _opts.success.call(this, JSON.parse(xhr.responseText))
                } else {
                    _opts.error.call(this, xhr.responseText)

                }
            }
        }
        _opts.type == 'GET' ? xhr.send() : xhr.send(_opts.data)
    }
    return {
        open: function (opts) {
            open(opts)
        }
    }
}()