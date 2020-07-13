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
                                <label for="debtorId" class="control-label ">资金调入公司:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="debtorId"
                                            name="debtorId">
                                        <option value="">==请选择==</option>
                                        <#if partyAs??>
                                            <#list partyAs as item>
                                                <option value="${item.id}" ${((debtorId==item.id+"")?string("selected",""))!}>${item.name}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="leaderId" class="control-label ">资金调出公司:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="leaderId"
                                            name="leaderId">
                                        <option value="">==请选择==</option>
                                        <#if partyAs??>
                                            <#list partyAs as item>
                                                <option value="${item.id}" ${((leaderId==item.id+"")?string("selected",""))!}>${item.name}</option>
                                                <#--<#if  leaderId??&&leaderId==item.id>-->
                                                    <#--<option value="${item.id}" selected>${item.name}</option>-->
                                                <#--<#else>-->
                                                    <#--<option value="${item.id}">${item.name}</option>-->
                                                <#--</#if>-->
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="returnTime" class="control-label ">计息开始时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="startTime" id="startTime"
                                           readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="returnTime" class="control-label ">计息结束时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="hidden" id="currentName" value="${currentUser.username}">
                                    <input type="text" class="form-control pull-right" name="returnTime" id="returnTime"
                                           readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="state" class="control-label ">是否还完:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="state" name="state">
                                        <option value="">==请选择==</option>
                                        <@dic type="loan_state" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="state" class="control-label ">是否有警告:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="isWarn" name="isWarn">
                                        <option value="">==请选择==</option>
                                        <@dic type="warning_state" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="status" class="control-label ">审核状态:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="status" name="state">
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
                        <@shiro.hasPermission name="Loan:loan:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Loan:loan:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Loan:loan:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/loan/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Loan:loan:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/loan/excelIn"></@krt.excelIn>
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
            //初始化datatable
        var datatable;
        $(function () {
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/loan/list",
                    type: "post",
                    data: function (d) {
                        d.debtorId = $("#debtorId").val();
                        d.leaderId = $("#leaderId").val();
                        d.isWarn=$("#isWarn").val();
                        d.startTime = $("#startTime").val();
                        d.returnTime = $("#returnTime").val();
                        d.state = $("#state").val();
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
                    {title: "协议编号", data: "number"},
                    {title: "资金调入公司", data: "debtorName"},
                    {title: "资金调出公司", data: "leaderName"},
                    {title: "贷款金额", data: "amount"},
                    {title: "计息时间", data: "tradeTime"},
                    {title: "年利率(%)", data: "rate"},
                    {title: "应归还时间", data: "returnTime"},
                    {title: "应该归还本金", data: "shouldAmount"},
                    {title: "应该归还利息", data: "shouldRate"},
                    {title: "已经归还本金", data: "realityAmount"},
                    {title: "已经归还利息", data: "realityRate"},
                    {title: "创建人", data: "createBy"},
                    {title: "申请人", data: "applyUser"},
                    {
                        title: "当前任务名称",
                        data: "taskName",
                        render: function (data) {
                            return '<span class="badge badge-primary">' + data + '</span>';
                        }
                    },
                    {
                        title: "是否还完", data: "state", render: function (data) {
                            return krt.util.getDic('loan_state ', data);
                        }
                    },
                    {title: "审核状态", data: "status", render: function (data) {
                            return krt.util.getDic('loan_approval ', data);
                        }
                            },
                    {title: "备注", data: "remark"},
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            var res="";
                            if (row.instanceId) {
                                res += '<@shiro.hasPermission name="Loan:loan:list">'
                                    + '<button class="btn btn-xs btn-primary historyBtn" rid="' + row.instanceId + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>审批历史'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';

                                res += '<@shiro.hasPermission name="Loan:loan:list">'
                                    + '<button class="btn btn-xs btn-primary showBtn" rid="' + row.instanceId +'">'
                                    + '<i class="fa fa-trash fa-btn"></i>进度查看'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';

                                if (row.taskName.indexOf('已结束') === -1) {

                                    res += ' <@shiro.hasPermission name="Loan:loan:list">'
                                        + '<button class="btn btn-xs btn-primary cancelBtn" rid="' + row.instanceId + '" createBy="' + row.createBy+ '">'
                                        + '<i class="fa fa-edit fa-btn"></i>撤销'
                                        + '</button>'
                                        + '</@shiro.hasPermission>';
                                    var suspendOrActive = row.suspendState === '2' ? '激活' : '挂起';
                                    var icon = row.suspendState === '2' ? 'fa fa-check' : 'fa fa-stop';
                                    res +=+ '<@shiro.hasPermission name="Leave:leave:list">'
                                        + '<button class="btn btn-xs btn-primary suspendOrActiveBtn" rid="' + row.instanceId + '" createBy="' + row.createBy+ '" suspendState="' +  row.suspendState +   '">'
                                        + '<i class="fa fa-trash fa-btn"></i>'+suspendOrActive
                                        + '</button>'
                                        + '</@shiro.hasPermission>';
                                }

                            }else{
                                res += '<@shiro.hasPermission name="Loan:loan:commit">'
                                    + '<button class="btn btn-xs btn-primary commitBtn" rid="' + row.id + '" createBy="' + row.createBy + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>提交审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';

                                res+=' <@shiro.hasPermission name="Loan:loan:update">'
                                    + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>修改'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="Loan:loan:delete">'
                                    + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>删除'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }

                                res+= '<@shiro.hasPermission name="Loan:loan:returnAmount">'
                                + '<button class="btn btn-xs btn-success returnBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-plus fa-btn"></i>还款'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="Loan:loan:list">'
                                + '<button class="btn btn-xs btn-success searchBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-plus fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                +'<button type="button" id="loanExcelBtn" class="btn btn-xs bg-orange"  rid="' + row.id + '">'
                                +'<i class="fa fa-cloud-download fa-btn"></i> 导出借款协议'
                                + '</button>'
                                +'<button type="button" id="loanCheckExcelBtn" class="btn btn-xs bg-orange"  rid="' + row.id + '">'
                                +'<i class="fa fa-cloud-download fa-btn"></i> 导出借款审批表'
                                + '</button>'
                                +'<button type="button" id="loanJoinExcelBtn" class="btn btn-xs bg-orange"  rid="' + row.id + '">'
                                +'<i class="fa fa-cloud-download fa-btn"></i> 导出借款申请表'
                                + '</button>'
                                +'<button type="button" id="loanApprovalExcelBtn" class="btn btn-xs bg-orange"  rid="' + row.id + '">'
                                +'<i class="fa fa-cloud-download fa-btn"></i> 导出资金划拨表'
                                + '</button>';
                            return res;
                        }
                    }
                ]
            });


            //提交审核
            $(document).on("click", ".commitBtn", function () {
                var id = $(this).attr("rid");
                var createBy = $(this).attr("createBy");
                var username = $("#currentName").val();

                alert(createBy+"createBy");
                alert(username+"username");
                if (createBy !== username) {
                    alert("不允许非创建人提交申请！");
                    return;
                }
                krt.layer.confirm("确认要提交申请吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/loan/submitApply",
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

            $(document).on("click", ".historyBtn", function () {
                var instanceId = $(this).attr("rid");
                var url = '${basePath}/process/historyList/' + instanceId;
                top.krt.layer.openDialog("查看审批历史", url, "1000px", "500px");
            });


            $(document).on("click", ".showBtn", function () {
                var instanceId = $(this).attr("rid");
                var url = '${basePath}/process/processImg/' + instanceId;
                top.krt.layer.openDialog("查看流程图", url, "1000px", "500px");
            });




            //审核
            $(document).on("click", ".checkBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("审核", "${basePath}/gck/loan/check?id=" + id, "1000px", "500px");
            });

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });


            //还款
            $(document).on("click", ".returnBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改资金划拨", "${basePath}/gck/loan/returnAmount?id=" + id, "1000px", "500px");
            });




            //导出
            $(document).on("click", "#loanExcelBtn", function () {
                var id = $(this).attr("rid");
                window.location.href = "${basePath}/gck/loan/loanExcelOut?id="+id;
            });

            //导出
            $(document).on("click", "#loanCheckExcelBtn", function () {
                var id = $(this).attr("rid");
                window.location.href = "${basePath}/gck/loan/checkExcelOut?id="+id;
            });


            //导出
            $(document).on("click", "#loanJoinExcelBtn", function () {
                var id = $(this).attr("rid");
                window.location.href = "${basePath}/gck/loan/joinExcelOut?id="+id;
            });




            //导出
            $(document).on("click", "#loanApprovalExcelBtn", function () {
                var id = $(this).attr("rid");
                window.location.href = "${basePath}/gck/loan/approvalExcelOut?id="+id;
            });


            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增资金划拨", "${basePath}/gck/loan/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改资金划拨", "${basePath}/gck/loan/update?id=" + id, "1000px", "500px");
            });


            //查看明细
            $(document).on("click", ".searchBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("归还本金和利息", "${basePath}/gck/loanReturn/list?loanId=" + id)
            });



            //查看打印
            $(document).on("click", ".printBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("查看打印", "${basePath}/gck/loan/print?loanId=" + id)
            });


            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gck/loan/delete",
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
                            url: "${basePath}/gck/loan/deleteByIds",
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
