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
                                            <span class="form-required">*</span>组织名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" value="${entity.name!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            编号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="creditCode" name="creditCode"
                                                   value="${entity.creditCode!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            开户行：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="bankName" name="bankName" value="${entity.bankName!} class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            银行账户：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="bankAccount" name="bankAccount"
                                                   value="${entity.bankAccount!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            地址：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="address" name="address" value="${entity.address!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>是否是内部组织：
                                        </label>
                                        <div class="col-sm-8">
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
                                                           <#--value="${item.code}"  ${((entity.isInternal==item.code)?string("checked",""))!}> ${item.name}-->
                                                <#--</#list>-->
                                            <#--</@dic>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="pname" class="control-label col-sm-4">
                                        备注：
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" id="note" name="note" value="${entity.note!}"
                                               class="form-control">
                                    </div>
                                </div>
                            </div>
                            </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id" value="${entity.id!}">
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
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/entity/update",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                       krt.table.reloadTable();
                        krt.layer.close(index);
                        /*history.go(-1);*/

                      /*  productModelTable.draw(false);*/

                    }
                }
            });
        }
    </script>
</@footer>

