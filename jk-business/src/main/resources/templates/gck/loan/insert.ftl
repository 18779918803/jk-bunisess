<#include "/common/layoutForm.ftl">
<@header></@header>
<@body >
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">

                           <#-- <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>编号：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="number" name="number" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>-->

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
                                                        <option value="${item.id}">${item.name}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>资金调入公司开户账号：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="debtorBankId"
                                                    name="debtorBankId" required>
                                                <option value="">==请选择==</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>



                            </div>

                            <div class="row">
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
                                                        <option value="${item.id}">${item.name}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>


                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>资金调出公司开户账号：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="leaderBankId"
                                                    name="leaderBankId" required>
                                                <option value="">==请选择==</option>
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
                                            <input type="text" id="amount" name="amount" class="form-control" required>
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
                                                       id="tradeTime" readonly
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required>
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
                                            <input type="text" id="rate" name="rate" class="form-control" required>
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
                                                       id="returnTime" readonly
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <#--<div class="col-sm-6">-->
                                    <#--<div class="form-group">-->
                                        <#--<label for="pname" class="control-label col-sm-4">-->
                                            <#--是否还完：-->
                                        <#--</label>-->
                                        <#--<div class="col-sm-8">-->
                                            <#--<select class="form-control select2" style="width: 100%" id="state"-->
                                                    <#--name="state">-->
                                                <#--<option value="">==请选择==</option>-->
                                                <#--<@dic type="loan_state" ; dicList>-->
                                                    <#--<#list dicList as item>-->
                                                        <#--<option value="${item.code}">${item.name}</option>-->
                                                    <#--</#list>-->
                                                <#--</@dic>-->
                                            <#--</select>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            用途：
                                        </label>
                                        <div class="col-sm-10">
                                            <textarea rows="2" name="remark" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 隐藏域 -->
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
        
        $("#debtorId").change(function (e) {
            var debtorId = e.target.value; // 获取选中下拉框的值
            $.ajax({
                type: "GET",
                url: "${basePath}/gck/bankOrgan/getListByOrganId",
                dataType: 'json',
                data: {"organId": debtorId},
                success: function (data) {
                    if (data.length > 0) {
                        $("#debtorBankId").empty();
                        $("#debtorBankId").append(" <option value=''>==请选择==</option>");
                        $.each(data, function (index, item) {
                            $("#debtorBankId").append(new Option(item.bankName, item.id));
                        })
                    } else {
                        $("#debtorBankId").empty();
                        $("#debtorBankId").append(" <option value=''>==请选择==</option>");
                    }
                }
            });

        });

        $("#leaderId").change(function (e) {
            var leaderId = e.target.value; // 获取选中下拉框的值
            $.ajax({
                type: "GET",
                url: "${basePath}/gck/bankOrgan/getListByOrganId",
                dataType: 'json',
                data: {"organId": leaderId},
                success: function (data) {
                    if (data.length > 0) {
                        $("#leaderBankId").empty();
                        $("#leaderBankId").append(" <option value=''>==请选择==</option>");
                        $.each(data, function (index, item) {
                            $("#leaderBankId").append(new Option(item.bankName, item.id));
                        })
                    } else {
                        $("#leaderBankId").empty();
                        $("#leaderBankId").append(" <option value=''>==请选择==</option>");
                    }
                }
            });

        });



        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/loan/insert",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();
                        krt.layer.close(index);
                    }
                }
            });
        }
    </script>
</@footer>

