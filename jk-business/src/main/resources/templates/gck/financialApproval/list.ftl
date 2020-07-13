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
                                <input type="hidden" id="contractId" name="contractId" value="${contractId!}">
                                <label for="number" class="control-label ">审批编号:</label>
                                <input type="text" id="number" name="number" placeholder="审批编号" class="form-control">
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
                                <label for="date" class="control-label ">开始时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="startDate" id="startDate"
                                           readonly
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="date" class="control-label ">结束时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="endDate" id="endDate"
                                           readonly
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <#--  <div class="form-group">
                                  <label for="contractid" class="control-label ">合同:</label>
                                  <input type="text" id="contractid" name="contractid" placeholder="合同"
                                         class="form-control">
                              </div>-->


                            <div class="form-group">
                                <label for="contractName" class="control-label ">合同名称:</label>
                                <input type="text" id="contractName" name="contractName" placeholder="合同名称"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="contractNumber" class="control-label ">合同编号:</label>
                                <input type="text" id="contractNumber" name="contractNumber" placeholder="合同编号"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="status" class="control-label ">审批状态:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="status" name="status">
                                        <option value="">==请选择==</option>
                                        <@dic type="approval_state " ; dicList>
                                            <#list dicList as item>
                                                <#if status??&&item.code = status>
                                                    <option value="${item.code}" selected>${item.name}</option>
                                                <#else >
                                                    <option value="${item.code}">${item.name}</option>
                                                </#if>
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
                        <@shiro.hasPermission name="FinancialApproval:financialApproval:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="FinancialApproval:financialApproval:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="FinancialApproval:financialApproval:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/financialApproval/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="FinancialApproval:financialApproval:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/financialApproval/excelIn"></@krt.excelIn>
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
                    url: "${basePath}/gck/financialApproval/list",
                    type: "post",
                    data: function (d) {
                        d.number = $("#number").val();
                        d.amount = $("#amount").val();
                        d.startDate = $("#startDate").val();
                        d.endDate = $("#endDate").val();
                        d.contractId = $("#contractId").val();
                        d.contractNumber = $("#contractNumber").val();
                        d.partya = $("#partya").val();
                        d.partyb = $("#partyb").val();
                        d.contractName = $("#contractName").val();
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
                    {
                        "title": "序号",
                        "data": "name",
                        "render": function (data, type, full, meta) {
                            return meta.row + 1 + meta.settings._iDisplayStart;
                        }
                    },
                    {title: "审批编号", data: "number"},
                    {
                        title: "审批金额", data: "amount", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {title: "审批时间", data: "date"},
                    {title: "主项目", data: "mainProjectName"},
                    {title: "子项目", data: "subProjectName"},
                    {title: "合同名称", data: "contractName"},
                    {title: "合同编号", data: "contractNumber"},
                    {title: "甲方", data: "partyaName"},
                    {title: "乙方", data: "partybName"},
                    {title: "备注", data: "note"},
                    {
                        title: "审批状态", data: "status",
                        render: function (data) {
                            return krt.util.getDic('approval_state', data);
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            var res = ' <@shiro.hasPermission name="FinancialApproval:financialApproval:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="FinancialApproval:financialApproval:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';

                            if (row.status != '1') {
                                res += '<@shiro.hasPermission name="FinancialApproval:financialApproval:record">'
                                    + '<button class="btn btn-xs btn-success recordBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-search fa-btn"></i>审核记录'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
                            if (row.status == '1' || row.status == '4') {
                                res += '<@shiro.hasPermission name="FinancialApproval:financialApproval:commit">'
                                    + '<button class="btn btn-xs btn-success commitBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>提交审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else if (row.status == '2') {
                                res += '<@shiro.hasPermission name="FinancialApproval:financialApproval:check">'
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
                        url: "${basePath}/gck/financialApproval/commit",
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
                top.krt.layer.openDialog("审核", "${basePath}/gck/financialApproval/check?id=" + id, "1000px", "500px");
            });


            //查看审核记录
            $(document).on("click", ".recordBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("查看审核记录", "${basePath}/gck/financialApproval/record?id=" + id, "1000px", "500px");
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //新增
            $("#insertBtn").click(function () {

                var id = $("#contractId").val();
                if(id==null||id.trim()=='') {
                    top.krt.layer.openDialog("新增审批款", "${basePath}/gck/financialApproval/insert", "1000px", "500px");
                }else{
                    top.krt.layer.openDialog("新增审批款", "${basePath}/gck/financialApproval/insert?contractId="+id, "1000px", "500px");
                }
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改审批款", "${basePath}/gck/financialApproval/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/financialApproval/delete",
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
                            url: "${basePath}/gck/financialApproval/deleteByIds",
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
