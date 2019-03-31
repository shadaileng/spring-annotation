var APP = function () {

    let initiCheck = function () {
        // console.log('init iCheck')

        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });
        let _checkMaster = $('input[type="checkbox"].minimal.check-master')
        let _checkBox = $('input[type="checkbox"].minimal')
        $('input[type="checkbox"].minimal.check-master').on('ifClicked', function (e) {
            // 取消
            if(e.target.checked) {
                _checkBox.iCheck('uncheck')
            }
            // 选中
            else {
                _checkBox.iCheck('check')
            }
        })
    }

    let getCheckedId = function() {
        let ids = []
        $('input[type="checkbox"].minimal:checked').each((i, e) => {
            ids.push(e.dataset.id)
        })
        return ids
    }

    let deleteByIds = function (ids) {
        if (ids.length <= 0) {
            layer.open({content: '请至少选择一条记录删除', icon: 5, shift: 6}, function () {

            })
            return
        }
        layer.confirm('是否删除[' + ids + ']?', {icon: 3, title: '删除'}, function(index) {
            let loadingIndex;
            $.ajax({
                url: 'delete',
                type: 'POST',
                data: {ids: ids.toString()},
                beforeSend: function () {
                    loadingIndex = layer.msg('处理中', {icon: 16})
                },
                success: function (data) {
                    layer.close(loadingIndex);
                    if(data.code == 200) {
                        $('#modal-default').modal('hide')
                        layer.msg(data.msg, {time:2000, icon: 6, shift: 6}, function () {
                            TableUtils.firstPage() && window.location.reload()
                            // window.location.reload()
                        })
                    } else if (data.code == 500) {
                        layer.open({content: data.msg, icon: 5, shift: 6}, function () {

                        })
                    }
                },
                error: function () {
                    layer.close(loadingIndex)
                    layer.open({content: "处理异常", icon: 5, shift: 6}, function () {

                    })
                }
            })
        })
    }

    return {
        initModal: function (modalId, titleUpdate, titleAdd) {
            initModal(modalId, titleUpdate, titleAdd)
        },
        initiCheck: function () {
            initiCheck()
        },
        initComponents: function () {
            initiCheck()

            $('button[name=del]').off('click')
            $('button[name=del]').on('click', function (e) {
                let ids = e.target.dataset.id
                deleteByIds([ids]);
            })
            $('#batchDel').off('click')
            $('#batchDel').on('click', function (e) {
                let ids = getCheckedId()
                deleteByIds(ids);
            })
        }

    }
}();

var ModalUtil = function() {
    let defaultOptions = {
        data: {},
        target: '#modalId',
        title: {
            update: '更新',
            insert: '新增'
        },
        url: {
            update: 'update',
            add: 'add'
        },
        callback: function () {
            console.log("callback...")
        }
    }
    let self = this

    let setFormData = function (data, formId) {
        let names = []
        document.querySelectorAll(formId + ' [name]').forEach(e=>{
            names.push(e.name)
        })
        names = [...new Set(names)]
        for (let i = 0, l = names.length; i < l; i++)
            // for (let key in data)
            {
                key = names[i]
            // console.log(key, data[key])
            let tag = $(formId).find('[name="' + key + '"]');
            if (tag.length > 1) {
                tag.prop('checked', false);
                if(data[key]) {
                    let item = data[key].split(',');
                    item.forEach((el, i) => {
                        let e = $(formId).find('[name="' + key + '"][value="' + el + '"]');
                        e.prop('checked', true)
                    })
                }
            } else {
                let val
                if (key.lastIndexOf(".") > 0) {
                    val = data
                    key.split(".").forEach(el => {
                        if (val) {
                            val = val[el]
                        }
                    })
                } else {
                    val = data[key]
                }
                tag.val(val)
            }
        }
    }

    let _extends = function (src, target) {
        for (let i in src) {
            let val = src[i]
            target[i] = val
        }
        return target
    }
    /**
     * 模态框初始化
     * @param modalId
     * @param titleUpdate
     * @param titleAdd
     */
    let initModal = function (opts) {
        opts = _extends(opts, defaultOptions)
        let {target, title, data, url} = opts

        /**
         * modal显示回调处理
         */
        $(target).on('shown.bs.modal', (e)=>{
            let id = e.relatedTarget.getAttribute('data-id')
            let loadingIndex;
            let _title = ''
            // 更新
            if (id) {
                _title = title.update
                $.ajax({
                    url: id,
                    type: 'GET',
                    beforeSend: function () {
                        loadingIndex = layer.msg('处理中', {icon: 16})
                    },
                    success: function (result) {
                        layer.close(loadingIndex)
                        let _data = data
                        if (result.code === 200) {
                            console.log('查询成功: ')
                            _data = result.data
                        }
                        setFormData(_data, target + " form")
                    },
                    error: function () {
                        layer.close(loadingIndex)
                        console.log('查询失败')
                        setFormData(data, target + " form")
                        layer.open({content: '处理异常', icon: 5, shift: 6}, function () {

                        })
                    }
                })
            }
            // 新增
            else {
                _title = title.insert
                // setFormData(data, target + " form")
                setFormData(data, target + " form")
            }
            document.querySelector(target + ' .modal-title').innerHTML = _title
        })

        /**
         * 确定按钮事件
         */
        $(target + ' .modal-footer .btn-primary').on('click', e => {
            console.log("save...")
            let loadingIndex;
            let id = document.querySelector(target + ' form [name="id"]').value
            let uri = id === '' || id === 'undefined' ? url.add : url.update
            if (!layer) {
                console.log("layer 未加载")
                return
            }
            $.ajax({
                url: uri,
                type: 'POST',
                data: $('#inputForm').serialize(),
                beforeSend: function () {
                    loadingIndex = layer.msg('处理中', {icon: 16})
                },
                success: function (data) {
                    layer.close(loadingIndex);
                    if(data.code == 200) {
                        $('#modal-default').modal('hide')
                        layer.msg(data.msg, {time:2000, icon: 6, shift: 6}, function () {
                            defaultOptions.callback && defaultOptions.callback()
                            // TableUtils.firstPage()
                            // console.log("reflash")
                            // window.location.reload()
                        })
                    } else if (data.code == 500) {
                        layer.open({content: '_' + data.msg, icon: 5, shift: 6}, function () {

                        })
                    }
                },
                error: function () {
                    layer.close(loadingIndex)
                    layer.open({content: "处理异常", icon: 5, shift: 6}, function () {

                    })
                }
            })
        })
    }

    return {
        init: function (opts) {
            initModal(opts)
        }
    }
}()

$(function () {
    if(!layer) {
        console.log("layer 未加载...")
        return
    }
    if(!jQuery) {
        console.log("jQuery 未加载...")
        return
    }
    console.log("加载 app")
    APP.initiCheck()
    APP.initComponents()
})