<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <!-- 搜索条件区 -->
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="name" class="control-label ">组织名称:</label>
                                <input type="text" id="name" name="name" placeholder="组织名称" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="creditCode" class="control-label ">编号:</label>
                                <input type="text" id="creditCode" name="creditCode" placeholder="编号"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="address" class="control-label ">地址:</label>
                                <input type="text" id="address" name="address" placeholder="地址" class="form-control">
                            </div>
                            <div class="form-group">
                                <label class="control-label ">是否是内部组织:</label>
                                <select class="form-control select2" style="width: 100%" id="isInternal" name="isInternal">
                                    <option value="">==请选择==</option>
                                    <@dic type="is_isinternal" ; dicList>
                                        <#list dicList as item>
                                            <option value="${item.code}">${item.name}</option>
                                        </#list>
                                    </@dic>
                                </select>
                                <#--<@dic type="is_isinternal" ; dicList>-->
                                    <#--<#list dicList as item>-->
                                        <#--<input type="radio" name="isInternal" class="icheck"-->
                                               <#--value="${item.code}"> ${item.name}-->
                                    <#--</#list>-->
                                <#--</@dic>-->
                            </div>
                            <div class="form-group">
                                <label for="status" class="control-label ">状态:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="status" name="status">
                                        <option value="">==请选择==</option>
                                        <@dic type="approval_state" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                        <i class="fa fa-search fa-btn"></i>搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <!-- 工具按钮区 -->
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="entity:entity:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="entity:entity:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="entity:entity:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/entity/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="entity:entity:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/entity/excelIn"></@krt.excelIn>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover"></table>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/entity/list",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.creditCode = $("#creditCode").val();
                        d.address = $("#address").val();
                        d.bankAccount = $("#bankAccount").val();
                        d.note = $("#note").val();
                        d.isInternal = $("input[name='isInternal']:checked").val();
                        d.status = $("#status").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: "id", data: "id"},
                    {title: "组织名称", data: "name"},
                    {title: "编号", data: "creditCode"},
                    {title: "地址", data: "address"},
                    {title: "银行账户", data: "bankAccount"},
                    {title: "备注", data: "note"},
                    {
                        title: "是否是内部组织", data: "isInternal",
                        render: function (data) {
                            return krt.util.getDic('is_isinternal', data);
                        }
                    },

                    {
                        title: "状态", data: "status",
                        render: function (data) {
                            return krt.util.getDic('approval_state', data);
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return ' <@shiro.hasPermission name="entity:entity:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="entity:entity:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        }
                    }
                ]
            });



            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增组织", "${basePath}/gck/entity/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改组织", "${basePath}/gck/entity/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/entity/delete",
                        data: {"id": id},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
            });

            //批量删除
            $("#deleteBatchBtn").click(function () {
                var ids = getIds();
                if (ids == "") {
                    krt.layer.error("请选择要删除的数据!");
                    return false;
                } else {
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/gck/entity/deleteByIds",
                            data: {"ids": ids},
                            success: function (rb) {
                                krt.layer.msg(rb.msg);
                                if (rb.code == 200) {
                                    krt.table.reloadTable(ids);
                                }
                            }
                        });
                    });
                }
            });
        });
    </script>
</@footer>
