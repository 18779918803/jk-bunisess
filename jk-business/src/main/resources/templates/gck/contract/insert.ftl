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

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>合同名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="contractname" name="contractname"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>合同编号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="number" name="number" class="form-control" required>
                                        </div>
                                    </div>
                                </div>

                            </div>


                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>主项目：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="mainprojectid"
                                                    name="mainprojectid" required>
                                                <option value="">==请选择==</option>
                                                <#if mainProjects??>
                                                    <#list mainProjects as item>
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
                                            子项目：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="projectid"
                                                    name="projectid">
                                                <option value="">==请选择==</option>
                                                <#--<#if subProject??>
                                                    <#list subProject as item>
                                                        <option value="${item.id}">${item.name}</option>
                                                    </#list>
                                                </#if>-->
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>合同类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="type"
                                                    name="type" required>
                                                <option value="">==请选择==</option>
                                                <@dic type="contract_type" ; dicList>
                                                    <#list dicList as item>
                                                        <option value="${item.code}">${item.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>合同状态：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="state"
                                                    name="state" required>
                                                <option value="">==请选择==</option>
                                                <@dic type="contract_state" ; dicList>
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


                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>甲方：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="partya"
                                                    name="partya" required>
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
                                            <span class="form-required">*</span>乙方：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="partyb"
                                                    name="partyb" required>
                                                <option value="">==请选择==</option>
                                                <#if partyBs??>
                                                    <#list partyBs as item>
                                                        <option value="${item.id}">${item.name}</option>
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
                                            <span class="form-required">*</span>中标价：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="price" name="price" class="form-control" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                          乙方子公司：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="partyc"
                                                    name="partyc" >
                                                <option value="">==请选择==</option>
                                                <#if partyBs??>
                                                    <#list partyBs as item>
                                                        <option value="${item.id}">${item.name}</option>
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
                                            合同签订时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="signdate"
                                                       id="signdate" readonly
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            竣工时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="completiondate"
                                                       id="completiondate" readonly
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
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
                                            结算价：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="auditprice" name="auditprice" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            最终结算价：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="finalauditprice" name="finalauditprice"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">


                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            完工付款比例：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="donepayrate" name="donepayrate"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            验收付款比例：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="payrate" name="payrate"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>


                            </div>


                            <div class="row">
                                <div class="col-sm-6 qualitydepositrate" hidden >
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            质保金比例：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="qualitydepositrate" name="qualitydepositrate"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 qualityperiod" hidden >
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            质保期：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="qualityperiod" name="qualityperiod"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-sm-6 qualityfixpay" hidden >
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            质保期维修费：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="qualityfixpay" name="qualityfixpay"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            备注：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="note" name="note" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">

                                <div class="col-sm-6 supervisionrate" hidden >
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            监理中标费率：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="supervisionrate" name="supervisionrate"
                                                   class="form-control">
                                        </div>
                                    </div>

                                    <div class="col-sm-6 checkrate" hidden >
                                        <div class="form-group">
                                            <label for="pname" class="control-label col-sm-4">
                                                检测中标费率：
                                            </label>
                                            <div class="col-sm-8">
                                                <input type="text" id="checkrate" name="checkrate" class="form-control">
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

        $("#type").change(function (e) {
            var typeId = e.target.value; // 获取选中下拉框的值
            if (typeId == 7 || typeId == 8) {
                $(".supervisionrate").hide();
                $(".checkrate").hide();
                $(".qualityperiod").show();
                $(".qualitydepositrate").show();
                $(".qualityfixpay").show();
                $("#donepayrate").val(75);
                $("#payrate").val(80);
                $("#qualitydepositrate").val(5);
                $("#qualityperiod").val(12);
            } else if (typeId == 17) {
                $(".qualityperiod").hide();
                $(".qualitydepositrate").hide();
                $(".qualityfixpay").hide();
                $(".supervisionrate").show();
                $("#donepayrate").val("");
                $("#payrate").val("");
            } else if (typeId == 18) {
                $(".qualityperiod").hide();
                $(".qualitydepositrate").hide();
                $(".qualityfixpay").hide();
                $(".supervisionrate").hide();
                $(".checkrate").show();
                $("#donepayrate").val("");
                $("#payrate").val("");
            }else{
                $(".qualityperiod").hide();
                $(".qualitydepositrate").hide();
                $(".qualityfixpay").hide();
                $(".supervisionrate").hide();
                $(".checkrate").hide();
                $("#donepayrate").val("");
                $("#payrate").val("");
                $("#qualitydepositrate").val("");
                $("#qualityperiod").val("");
            }


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
                            $("#projectid").append(new Option(item.name, item.id));
                        })
                    } else {
                        $("#projectid").empty();
                        $("#projectid").append(" <option value=''>==请选择==</option>");
                    }
                }
            });
        });


        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/contract/insert",
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

