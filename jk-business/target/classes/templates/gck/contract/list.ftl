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
                                <label for="number" class="control-label ">合同编号:</label>
                                <input type="text" id="number" name="number" placeholder="合同编号" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="type" class="control-label ">合同类型:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="type" name="type">
                                        <option value="">==请选择==</option>
                                        <@dic type="contract_type" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="state" class="control-label ">合同状态:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="state" name="state">
                                        <option value="">==请选择==</option>
                                        <@dic type="contract_state" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="mainprojectid" class="control-label ">主项目:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="mainprojectid"
                                            name="mainprojectid">
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
                                <label for="projectid" class="control-label ">子项目:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="projectid"
                                            name="projectid">
                                        <option value="">==请选择==</option>
                                        <#-- <#list dicList as item>
                                             <option value="${item.id}">${item.name}</option>
                                         </#list>-->
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="partya" class="control-label ">甲方:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="partya" name="partya">
                                        <option value="">==请选择==</option>
                                        <#if partyAs??>
                                            <#list partyAs as item>
                                                <option value="${item.id}">${item.name}</option>
                                            </#list>
                                        </#if>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="partyb" class="control-label ">乙方:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="partyb" name="partyb">
                                        <option value="">==请选择==</option>
                                        <#if partyBs??>
                                            <#list partyBs as item>
                                                <option value="${item.id}">${item.name}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="status" class="control-label ">审批状态:</label>
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
                                <label for="status" class="control-label ">是否警告:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="warning"
                                            name="warning">
                                        <option value="">==请选择==</option>
                                        <@dic type="warning_state" ; dicList>
                                            <#list dicList as item>
                                                <#if  warning??&&warning==item.code>
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
                                <label for="contractname" class="control-label ">合同名称:</label>
                                <input type="text" id="contractname" name="contractname" placeholder="合同名称"
                                       class="form-control">
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
                        <@shiro.hasPermission name="contract:contract:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="contract:contract:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="contract:contract:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/contract/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="contract:contract:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/contract/excelIn"></@krt.excelIn>
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

        // 添加金额格式化
        jQuery.extend({
            formatFloat: function (src, pos) {
                var num = parseFloat(src).toFixed(pos);
                num = num.toString().replace(/\$|\,/g, '');
                if (isNaN(num)) num = "0";
                sign = (num == (num = Math.abs(num)));
                num = Math.floor(num * 100 + 0.50000000001);
                cents = num % 100;
                num = Math.floor(num / 100).toString();
                if (cents < 10) cents = "0" + cents;
                for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                    num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
                return (((sign) ? '' : '-') + num + '.' + cents);
            }
        });
        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/contract/list",
                    type: "post",
                    data: function (d) {
                        d.number = $("#number").val();
                        d.type = $("#type").val();
                        d.state = $("#state").val();
                        d.projectid = $("#projectid").val();
                        d.partya = $("#partya").val();
                        d.partyb = $("#partyb").val();
                        d.status = $("#status").val();
                        d.mainprojectid = $("#mainprojectid").val();
                        d.contractname = $("#contractname").val();
                        d.warning = $("#warning").val();
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
                    /*   {title: "id", data: "id"},*/
                    {title: "合同编号", data: "number"},
                    {title: "主项目", data: "mainProjectName"},
                    {title: "子项目", data: "subProjectName"},
                    {title: "合同名称", data: "contractname"},
                    {title: "甲方", data: "partyaName"},
                    {title: "乙方", data: "partybName"},
                    {
                        title: "警告", data: "isWarn", render: function (data) {
                            if(data==2){
                                return "付款大于审批款";
                            }else{
                                return ""
                            }
                        }
                    },
                    {
                        title: "合同类型", data: "type",
                        render: function (data) {
                            return krt.util.getDic('contract_type', data);
                        }
                    },
                    {
                        title: "中标价", data: "price", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "结算价", data: "auditprice", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "合同状态", data: "state",
                        render: function (data) {
                            return krt.util.getDic('contract_state', data);
                        }
                    },
                    {
                        title: "审批金额", data: "approvalsum", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "付款金额", data: "paysum", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "发票金额", data: "billsum", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },


                    {
                        title: "审批状态", data: "status",
                        render: function (data) {
                            return krt.util.getDic('approval_state', data);
                        }
                    },


                    {
                        title: "审批款和付款", data: "id", orderable: false, width: 120,
                        "render": function (data, type, row) {
                            return ' <@shiro.hasPermission name="FinancialApproval:financialApproval:list">'
                                + '<button class="btn btn-xs btn-success financialApprovalBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-search fa-btn"></i>审批款'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="Payment:payment:list">'
                                + '<button class="btn btn-xs btn-success paymentBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-search fa-btn"></i>付款'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        }
                    },

                    {
                        title: "操作", data: "id", orderable: false, width: 120,
                        "render": function (data, type, row) {
                            var res;
                            res = ' <@shiro.hasPermission name="contract:contract:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="contract:contract:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';

                            if (row.status != '1') {
                                res += '<@shiro.hasPermission name="contract:contract:record">'
                                    + '<button class="btn btn-xs btn-success recordBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-search fa-btn"></i>审核记录'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
                            if (row.status == '1' || row.status == '4') {
                                res += '<@shiro.hasPermission name="contract:contract:commit">'
                                    + '<button class="btn btn-xs btn-success commitBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>提交审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else if (row.status == '2') {
                                res += '<@shiro.hasPermission name="contract:contract:check">'
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


            //通过主项目id获取子项目
            $("#mainprojectid").change(function (e) {
                var mainProjectId = e.target.value; // 获取选中下拉框的值

                if (mainProjectId == null) {
                    mainProjectId = 0;
                }
                $.ajax({
                    type: "GET",
                    url: "${basePath}/gck/contract/getSubProject",
                    dataType: 'json',
                    data: {"mainProjectId": mainProjectId},
                    success: function (data) {
                        if (data.length > 0) {
                            $("#projectid").empty();
                            $("#projectid").append(" <option value=''>==请选择==</option>");
                            $.each(data, function (index, item) {
                                if (item.id ==#{projectid}) {
                                    $("#projectid").append(new Option(item.name, item.id, true, true));
                                } else {
                                    $("#projectid").append(new Option(item.name, item.id));
                                }

                            })
                        } else {
                            $("#projectid").empty();
                            $("#projectid").append(" <option value=''>==请选择==</option>");
                        }
                    }
                });


            });

            //提交审核
            $(document).on("click", ".commitBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定提交审核吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/contract/commit",
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
                top.krt.layer.openDialog("审核", "${basePath}/gck/contract/check?id=" + id, "1000px", "500px");
            });


            //查看审核记录
            $(document).on("click", ".recordBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("查看审核记录", "${basePath}/gck/contract/record?id=" + id, "1000px", "500px");
            });


            //查看审批款
            $(document).on("click", ".financialApprovalBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("审批款管理", "${basePath}/gck/financialApproval/list?id=" + id);
            });


            //查看付款
            $(document).on("click", ".paymentBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("付款管理", "${basePath}/gck/payment/list?id=" + id)
            });

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增合同", "${basePath}/gck/contract/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改合同", "${basePath}/gck/contract/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/contract/delete",
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
                            url: "${basePath}/gck/contract/deleteByIds",
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

            $("#mainprojectid").change();
        });
    </script>
</@footer>
