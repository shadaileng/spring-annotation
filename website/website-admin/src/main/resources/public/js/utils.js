var TableUtils = function () {
    let defaultOptions = {
        mode: 'local',          // local | server
        url: '',                // server mode
        paramEl: null,
        data: {},               //
        head: [],
        page: {
            hasPre: false,
            hasNext: false,
            pageCount: 1,
            index: 1,
            pageSize: 5,
            total: 0
        },
        columns: [],            // 列
        target: '#tableEL',     // 初始化检查target
        status: false,          // 插件状态
        isBoot: false,           // 是否引入Bootstrap
        callback: console.log
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
        // console.log(opts)
        if (document.querySelector(opts.target) == null) {
            console.log('未指定目标元素|#tableEL')
            return
        }
        if (opts.mode === 'local') {
            handleData(opts)
        } else if (opts.mode === 'server') {
            pageTo(opts.url, 1)
        }


    }
    let pageTo = function (url, start) {
        let _data = {
            start: start,
            length: defaultOptions.page.pageSize
        }
        let _param_target = defaultOptions.paramEl
        if (_param_target != null) {
            document.querySelectorAll(_param_target).forEach(el=>{
                _data[el.name] = el.value.trim()
            })
        }
        AJAX.open({
            url: url,
            data: _data,
            success: function (result) {
                defaultOptions.data = result
                // console.log(result)
                handlePage(result)
                handleData()
            }
        })
    }
    let handlePage = function (data) {
        defaultOptions.page.total = data.total
        let total = data.total
        let pageSize = defaultOptions.page.pageSize
        defaultOptions.page.pageCount = Math.floor((total - 1) / pageSize) + 1
        defaultOptions.page.hasPre = defaultOptions.page.index != 1
        defaultOptions.page.hasNext = defaultOptions.page.index != defaultOptions.page.pageCount
        // defaultOptions.page.index = index
        // let start = (index - 1) * pageSize
        // console.log(defaultOptions)
    }
    let setFooter = function () {
        let {target, columns, page} = defaultOptions

        let _td = document.createElement("td")
        _td.setAttribute("colspan", columns.length)

        let _pagination = document.createElement('nav')
        _pagination.classList.add('pull-right')
        _pagination.setAttribute('aria-label', 'Page navigation')
        let _ul = document.createElement('ul')
        _ul.classList.add('pagination')
        _ul.style.margin = 'auto'

        let _pre_li = document.createElement('li')
        page.hasPre || (_pre_li.classList.add('disabled'))
        let _pre_a = document.createElement('a')
        _pre_a.href = '#'
        _pre_a.setAttribute('aria-label', 'Previous')
        _pre_a.dataset.index = '-1'
        _pre_a.innerHTML = '&laquo;'
        _pre_li.appendChild(_pre_a)
        _ul.appendChild(_pre_li)


        for (let i = 1, l = page.pageCount; i <= l; i++) {
            let _li = document.createElement('li')
            page.index == i && (_li.classList.add('active'))
            let _li_a = document.createElement('a')
            _li_a.dataset.index = i
            _li_a.href = '#'
            _li_a.innerHTML = i
            _li.appendChild(_li_a)
            _ul.appendChild(_li)
        }


        let _next_li = document.createElement('li')
        page.hasNext || (_next_li.classList.add('disabled'))
        let _next_a = document.createElement('a')
        _next_a.href = '#'
        _next_a.setAttribute('aria-label', 'Next')
        _next_a.dataset.index = '+1'
        _next_a.innerHTML = '&raquo;'
        _next_li.appendChild(_next_a)
        _ul.appendChild(_next_li)

        _pagination.appendChild(_ul)
        _td.appendChild(_pagination)

        let _tr = document.createElement('tr')
        _tr.appendChild(_td)
        let _foot = document.querySelector(target + ' tfoot')
        if (_foot === null) {
            _foot = document.createElement('tfoot')
            _foot.appendChild(_tr)
            document.querySelector(target).appendChild(_foot)
        } else {
            let _foot_ = document.createElement('tfoot')
            _foot_.appendChild(_tr)
            _foot.replaceWith(_foot_)
        }

        document.querySelectorAll(target + ' tfoot tr td nav ul li').forEach(e=>{
            e.addEventListener('click', (event)=>{
                if (event.target.parentElement.className.match('disabled') === null && event.target.parentElement.className.match('active') === null) {
                    let index = event.target.dataset.index
                    let _index = defaultOptions.page.index
                    let pageSize = defaultOptions.page.pageSize
                    if (index === '-1' && index !== 1) {
                        _index =  parseInt(_index) - 1
                    }
                    if (index === '+1' && index !== defaultOptions.page.pageCount) {
                        _index = parseInt(_index) + 1
                    }
                    if (index !== '-1' && index !== '+1'){
                        _index = index
                    }
                    let start = (parseInt(_index) - 1) * pageSize + 1
                    // console.log('page to: ' + _index)
                    defaultOptions.page.index = _index
                    pageTo(defaultOptions.url, start)
                } else {
                    // console.log('page to disabled')
                }
            })
        })

    }
    let handleData = function () {
        let {target, columns, data} = defaultOptions
        // console.log(data)
        if (columns.length === 0 && data.data.length > 0) {
            console.log('[WARN] 列名未定义')
            Object.keys(data.data[0]).forEach(e=>{
                let el = {
                    data: e,
                    type: 'string',
                    func: null
                }
                columns.push(el)
            })
            defaultOptions.columns = columns
            setHead(target, columns)
        } else {
            if (document.querySelectorAll(target + ' thead tr th').length !== columns.length) {
                // console.log('[WARN] 定义列数与表格列数不符')
            }
        }

        typeof($.fn.popover) === 'undefined' ? console.log('[WARN] Bootstrap未加载...') : defaultOptions.isBoot = true
        defaultOptions.isBoot ? document.querySelector(target).className = 'table table-bordered table-hover' : ''
        setTableData()
        setFooter()

        defaultOptions.callback.call(this)
    }
    let setHead = function (target, columns) {

        let _head = document.querySelector(target + ' thead tr')
        let _row = document.createElement('tr')
        columns.forEach((e)=>{
            let _col = document.createElement('th')
            _col.innerHTML = e.data
            _row.appendChild(_col)
        })
        _head.replaceWith(_row)
    }
    let setTableData = function () {
        let {target, columns, data} = defaultOptions
        let _data = data.data
        // console.log(data)
        if (_data === undefined || _data.length <= 0) {
            console.log('data为空')
            return
        }
        let selector = target + ' tbody'
        let _body = document.querySelector(selector)
        // let _columns = defaultOptions.columns
        let _body_ = document.createElement('tbody')
        _data.forEach((e, i) => {
            let _row = document.createElement('tr')
            columns.forEach((el, i)=>{
                let _col = document.createElement('td')
                let _content = ''
                if (el.type === 'func') {
                    _content = el.func(e)
                } else if(el.type === 'string') {
                    let _data = el.data
                    let _innerEl = e[_data]
                    if (_data.lastIndexOf('.') > 0) {
                        let _preper = _data.split(".")
                        let el = e
                        for (let i = 0, l = _preper.length; i < l; i++) {
                            el = el[_preper[i]]
                        }
                        _innerEl = el
                    }
                    _content = _innerEl === undefined ? '' :  _innerEl.toString()
                }
                // let _innerEl = e[el]
                // let _content = typeof _innerEl == 'function' ? _innerEl() :( _innerEl == undefined ? '' :  _innerEl.toString())
                _col.innerHTML = _content
                // console.log(_content)
                _row.appendChild(_col)
            })
            // console.log(_row)
            _body_.appendChild(_row)
        })
        _body.replaceWith(_body_)
    }
    return {
        init: function (opts) {
            init(opts)
        },
        setTableData: function (data, _tabSelector_) {
            setTableData(data, _tabSelector_)
        },
        firstPage: function () {
            defaultOptions.page.index = 1
            pageTo(defaultOptions.url, 1)
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
        error: function (code, error) {
            console.log('error[' + code + ']: ' + error)
        }
    }
    let open = function (opts) {
        for (let key in opts) {
            let val = opts[key]
            _opts[key] = val
        }
        if (_opts.url === '') {
            console.log('url为空')
            return false
        }
        let str = ''
        if (_opts.type === 'GET' && _opts.data !== null && _opts.data.length > 0) {
            for (let key in _opts.data) {
                str += key + '=' + _opts.data[key] + '&'
            }
            str = str.substr(0, str.length - 1)
            _opts.url = _opts.url + '?' + str
        }
        let xhr = new XMLHttpRequest();
        xhr.open(_opts.type, _opts.url, _opts.async)
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 1){ // 1: 服务器连接已建立（open方法已经被调用）
                // console.log('1: 服务器连接已建立（open方法已经被调用）')
            } else if(xhr.readyState === 2) { // 2: 请求已接收（send方法已经被调用，并且头部和状态已经可获得）
                // console.log('2: 请求已接收（send方法已经被调用，并且头部和状态已经可获得）')
            } else if(xhr.readyState === 3) { // 3: 请求处理中（下载中，responseText 属性已经包含部分数据）
                // console.log('3: 请求处理中（下载中，responseText 属性已经包含部分数据）')
            } else if(xhr.readyState === 4) { // 4: 请求已完成，且响应已就绪（下载操作已完成）
                // console.log('4: 请求已完成，且响应已就绪（下载操作已完成）')
                if (xhr.status === 200 || xhr.status === 304) {
                    _opts.success.call(this, JSON.parse(xhr.responseText))
                } else if (xhr.status.toString().startsWith("5")){
                    _opts.error.call(this, xhr.status,"服务器异常")

                }
            }
        }
        _opts.type === 'GET' ? xhr.send() : xhr.send(_opts.data)
    }
    return {
        open: function (opts) {
            open(opts)
        }
    }
}()