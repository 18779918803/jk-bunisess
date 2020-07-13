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
                                            <span class="form-required">*</span>子项目编号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="number" name="number" value="${subProject.number!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>子项目名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" value="${subProject.name!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>主项目id：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="mainpid"
                                                    name="mainpid" required>
                                                <option value="">==请选择==</option>
                                                    <#list mainProjects as item>
                                                        <option value="${item.id}" ${((subProject.mainpid==item.id)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>组织机构：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="owner"
                                                    name="owner" required>
                                                <option value="">==请选择==</option>
                                                    <#list entitys as item>
                                                        <option value="${item.id}" ${((subProject.owner==item.id)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>子项目类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="type"
                                                    name="type" required>
                                                <option value="">==请选择==</option>
                                                <@dic type="subProject_type" ; dicList>
                                                    <#list dicList as item>
                                                        <option value="${item.code}" ${((subProject.type==item.code)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            描述：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="des" name="des" value="${subProject.des!}"
                                                   class="form-control">
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
                                            <input type="text" id="note" name="note" value="${subProject.note!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <!-- 隐藏域 -->
                                <input type="hidden" name="id" id="id" value="${subProject.id!}">
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
                url: "${basePath}/gck/subProject/update",
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

