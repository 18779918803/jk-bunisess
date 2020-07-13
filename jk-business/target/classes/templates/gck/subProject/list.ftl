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
                                <label for="number" class="control-label ">子项目编号:</label>
                                <input type="text" id="number" name="number" placeholder="子项目编号" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">子项目名称:</label>
                                <input type="text" id="name" name="name" placeholder="子项目名称" class="form-control">
                            </div>

                            <div class="form-group">
                                <label for="status" class="control-label ">主项目:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="mainpid"
                                            name="mainpid">
                                        <option value="">==请选择==</option>
                                        <#if mainProjects??>
                                            <#list mainProjects as item>
                                                <#if  mainpid??&&mainpid==item.id>
                                                    <option value="${item.id}" selected>${item.name}</option>
                                                <#else>
                                                    <option value="${item.id}">${item.name}</option>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="status" class="control-label ">状态:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="status" name="status">
                                        <option value="">==请选择==</option>
                                        <@dic type="approval_state" ; dicList>
                                            <#list dicList as item>
                                                <#if  status??&&status==item.code>
                                                    <option value="${item.code}" selected>${item.name}</option>
                                                <#else>
                                                    <option value="${item.code}">${item.name}</option>
                                                </#if>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="owner" class="control-label ">组织机构:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="owner" name="owner">
                                        <option value="">==请选择==</option>
                                        <#list entitys as item>
                                            <option value="${item.id}">${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="type" class="control-label ">子项目类型:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="type" name="type">
                                        <option value="">==请选择==</option>
                                        <@dic type="subProject_type" ; dicList>
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
                        <@shiro.hasPermission name="Subject:subProject:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Subject:subProject:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Subject:subProject:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/subProject/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Subject:subProject:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/subProject/excelIn"></@krt.excelIn>
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
                    url: "${basePath}/gck/subProject/list",
                    type: "post",
                    data: function (d) {
                        d.number = $("#number").val();
                        d.name = $("#name").val();
                        d.status = $("#status").val();
                        d.owner = $("#owner").val();
                        d.type = $("#type").val();
                        d.mainpid = $("#mainpid").val();
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
                    {
                        "title": "序号",
                        "data": "name",
                        "render": function (data, type, full, meta) {
                            return meta.row + 1 + meta.settings._iDisplayStart;
                        }
                    },
                    /*       {title: "id", data: "id"},*/
                    {title: "子项目编号", data: "number"},
                    {title: "子项目名称", data: "name"},
                    {title: "主项目", data: "mainProjectName"},
                    {
                        title: "状态", data: "status",
                        render: function (data) {
                            return krt.util.getDic('approval_state', data);
                        }
                    },
                    {title: "组织机构", data: "entityName"},
                    {
                        title: "子项目类型", data: "type",
                        render: function (data) {
                            return krt.util.getDic('subProject_type', data);
                        }
                    },
                    {title: "描述", data: "des"},
                    {title: "备注", data: "note"},
                    {
                        title: "操作", data: "id", orderable: false, width: 200,
                        "render": function (data, type, row) {

                            var res;
                            res = ' <@shiro.hasPermission name="Subject:subProject:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="Subject:subProject:list">'
                                + '<button class="btn btn-xs btn-success findBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-search fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="Subject:subProject:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';

                            if (row.status != '1') {
                                res += '<@shiro.hasPermission name="Subject:subProject:record">'
                                    + '<button class="btn btn-xs btn-success recordBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-search fa-btn"></i>审核记录'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
                            if (row.status == '1' || row.status == '4') {
                                res += '<@shiro.hasPermission name="Subject:subProject:commit">'
                                    + '<button class="btn btn-xs btn-success commitBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>提交审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else if (row.status == '2') {
                                res += '<@shiro.hasPermission name="Subject:subProject:check">'
                                    + '<button class="btn btn-xs btn-success checkBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                            }
                            return res;

                        }
                    }
                ]
            });


            //提交审核
            $(document).on("click", ".commitBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定提交审核吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/subProject/commit",
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

            //审核
            $(document).on("click", ".checkBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("审核", "${basePath}/gck/subProject/check?id=" + id, "1000px", "500px");
            });

            //查看审核记录
            $(document).on("click", ".recordBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("查看审核记录", "${basePath}/gck/subProject/record?id=" + id, "1000px", "500px");
            });

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //查看合同
            $(document).on("click", ".findBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("合同管理", "${basePath}/gck/contract/list?id=" + id)
            });

            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增子项目", "${basePath}/gck/subProject/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改子项目", "${basePath}/gck/subProject/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/subProject/delete",
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
                            url: "${basePath}/gck/subProject/deleteByIds",
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
