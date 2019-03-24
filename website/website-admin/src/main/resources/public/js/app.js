var APP = function () {
    let setFormData = function (data, formId) {
        for (let key in data) {
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
                tag.val(data[key])
            }
        }
    }

    /**
     * 模态框初始化
     * @param modalId
     * @param titleUpdate
     * @param titleAdd
     */
    let initModal = function (modalId, titleUpdate, titleAdd) {
        /**
         * modal显示回调处理
         */
        $(modalId).on('shown.bs.modal', function (e) {
            let id = $(e.relatedTarget).data('id')
            let loadingIndex;
            // 更新
            if (id) {
                $(modalId + ' .modal-title').html(titleUpdate)
                $.ajax({
                    url: id,
                    type: 'GET',
                    beforeSend: function () {
                        loadingIndex = layer.msg('处理中', {icon: 16})
                    },
                    success: function (result) {
                        layer.close(loadingIndex)
                        console.log('查询成功: ')
                        setFormData(result, modalId + " form")
                    },
                    error: function () {
                        layer.close(loadingIndex)
                        console.log('查询失败')
                        setFormData({username:'',email: '', phone: ''}, modalId + " form")
                        layer.open({content: data.msg, icon: 5, shift: 6}, function () {

                        })
                    }
                })
            }
            // 新增
            else {
                $(modalId + ' .modal-title').html(titleAdd)
                console.log("清空表单")
                setFormData({username:'',email: '', phone: '', id: ''}, modalId + " form")
            }
        })
        /**
         * 确定按钮事件
         */
        $(modalId+ ' .modal-footer .btn-primary').on("click", function () {
            console.log("save...")
            let loadingIndex;
            let id = $(modalId + ' form [name="id"]').val()
            let uri = id == '' || id == 'undefined' ? 'add' : 'update'
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
                            TableUtils.firstPage()
                            // console.log("reflash")
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
                            TableUtils.firstPage()
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
})