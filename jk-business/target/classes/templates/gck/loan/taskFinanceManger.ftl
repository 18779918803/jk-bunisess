<#include "/common/layoutForm.ftl">
<@header></@header>
<@body >
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>申请人：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="hidden" id="type" name="type"
                                                   value="1" class="form-control" required>
                                            <input type="text" id="applyUserName" name="applyUserName"
                                                   value="${loan.applyUser!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>申请时间：
                                        </label>
                                        <div class="col-sm-10">

                                            <input type="text" class="form-control pull-right" name="applyTime"
                                                   id="startTime"
                                                   value="${(loan.applyTime?string("yyyy-MM-dd"))!}"
                                                   readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                   required>
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>

                                            <#--<input type="text" id="applyUserName" name="applyUserName"-->
                                            <#--value="${leave.applyUserName!}" class="form-control" required>-->
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>编号：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="number" name="number" class="form-control" value="${loan.number!}" required>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="pname" class="control-label col-sm-4">
                                        申请事由：
                                    </label>
                                    <div class="col-sm-8">
                                            <textarea rows="2" name="remark"
                                                      class="form-control">${loan.remark!}</textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>资金调入公司：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="debtorId"
                                                    name="debtorId" required>
                                                <option value="">==请选择==</option>
                                                <#if partyAs??>
                                                    <#list partyAs as item>
                                                        <option value="${item.id}" ${((loan.debtorId==item.id)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>资金调出公司：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="leaderId"
                                                    name="leaderId" required>
                                                <option value="">==请选择==</option>
                                                <#if partyAs??>
                                                    <#list partyAs as item>
                                                        <option value="${item.id}" ${((loan.leaderId==item.id)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>代款金额：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="amount" name="amount" value="${loan.amount!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>计息时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="tradeTime"
                                                       id="tradeTime"
                                                       value="${(loan.tradeTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>利息：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="rate" name="rate" value="${loan.rate!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>应归还时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="returnTime"
                                                       id="returnTime"
                                                       value="${(loan.returnTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="status" class="control-label col-sm-2">
                                            <span class="form-required">*</span>审核是否通过：
                                        </label>
                                        <div class="col-sm-10">
                                            <select class="form-control select2" style="width: 100%"
                                                    id="p_B_fianceMangerApproved"
                                                    name="p_B_financeMangerApproved" required>
                                                <option value="">==请选择==</option>
                                                <@dic type="approval_edit" ; dicList>
                                                    <#list dicList as item>
                                                        <option value="${item.code}">${item.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="approvalDesc" class="control-label col-sm-2">
                                            审核理由：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="approvalDes" name="approvalDes"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <!-- 隐藏域 -->
                            <input type="hidden" name="p_COM_comment" id="p_COM_comment"/>
                            <input type="hidden" name="id" id="id" value="${loan.id!}">
                            <input name="taskId" id="taskId" value="${taskId!}" type="hidden">
                            <input type="hidden" name="instanceId" id="instanceId" value="${loan.instanceId!}">
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var validateForm;
        $(function () {
            //验证表单
            validateForm = $("#krtForm").validate({});

        });

        //提交
        function doSubmit() {
            if ($('#approvalDes').val()) {
                $('#p_COM_comment').val($('#approvalDes').val());
            }
            var taskId = $("#taskId").val();
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/loan/complete/" + taskId,
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();
                        krt.layer.close(index);
                    }
                },error:function (rb) {
                    krt.layer.msg(rb.msg);
                }
            });
        }
    </script>
</@footer>

