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
                                           付款凭证号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="number" name="number" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>应付：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="amount" name="amount" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span> 实付 ：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="shouldAmount" name="shouldAmount"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            扣款：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="cutAmount" name="cutAmount" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            发票金额：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="billAmount" name="billAmount" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            扣款说明：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="cutNote" name="cutNote" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>交易时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="tradeTime"
                                                       id="tradeTime" readonly
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required>
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
                                            备注：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="note" name="note" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <#--    <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>合同：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="contractId" name="contractId" class="form-control"
                                                   required>
                                        </div>
                                    </div>
                                </div>-->

                            <#if contract??>

                            <#else>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="pname" class="control-label col-sm-4">
                                                <span class="form-required">*</span>合同：
                                            </label>
                                            <div class="col-sm-8">
                                                <button title="添加" type="button" id="insertContractBtn"
                                                        data-placement="left" data-toggle="tooltip"
                                                        class="btn btn-success btn-sm">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#if>


                            <div class="row">
                                <div class="col-sm-2">
                                </div>
                                <div class="col-sm-10">
                                    <table id="table-contract" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>合同编号</th>
                                            <th>合同名称</th>
                                            <th>主项目名称</th>
                                            <th>子项目名称</th>
                                            <th>甲方组织</th>
                                            <th>乙方组织</th>
                                            <th width="77">操作</th>
                                        </tr>
                                        </thead>
                                        <#if contract??>
                                            <tr class="tr-contract">
                                                <td> ${contract.number!}</td>
                                                <td> ${contract.contractname!}</td>
                                                <td> ${contract.mainProjectName!}</td>
                                                <td>${contract.subProjectName!}</td>
                                                <td>${contract.partyaName!}</td>
                                                <td>${contract.partybName!}</td>
                                                <td>

                                                </td>
                                                <input type="hidden" name="contractId" value="${contract.id!}"/>
                                            </tr>
                                        </#if>

                                    </table>
                                </div>
                            </div>

                            <div class="row">

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

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/payment/insert",
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

        $("#insertContractBtn").click(function () {
            var url = "gck/contract/select";
            krt.layer.open({
                type: 2,
                area: ['1000px', '500px'],
                title: "选择合同",
                maxmin: false,
                content: "${basePath}/" + url,
                btn: ['确定', '关闭'],
                yes: function (index, layero) {

                    var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                    var snumber = layero.find("iframe")[0].contentWindow.$("#snumber").val();
                    var scontractname = layero.find("iframe")[0].contentWindow.$("#scontractname").val();
                    var smainProjectName = layero.find("iframe")[0].contentWindow.$("#smainProjectName").val();
                    var ssubProjectName = layero.find("iframe")[0].contentWindow.$("#ssubProjectName").val();
                    var spartyaName = layero.find("iframe")[0].contentWindow.$("#spartyaName").val();
                    var spartybName = layero.find("iframe")[0].contentWindow.$("#spartybName").val();
                    alert("snumber" + snumber);
                    if (sId == null || sId == '') {
                        top.layer.msg("请选择合同");
                    } else {
                        var html = '';
                        html += '<tr class="tr-contract">\n' +
                            '   <td>' + snumber + '</td>\n' +
                            '   <td>' + scontractname + '</td>\n' +
                            '   <td>' + smainProjectName + '</td>\n' +
                            '   <td>' + ssubProjectName + '</td>\n' +
                            '   <td>' + spartyaName + '</td>\n' +
                            '   <td>' + spartybName + '</td>\n' +
                            '   <td>\n' +
                            '       <a class="btn btn-xs btn-danger deleteContractBtn">\n' +
                            '           <i class="fa fa-trash fa-btn"></i>删除\n' +
                            '       </a>\n' +
                            '   </td>\n' +
                            '   <input type="hidden" name="contractId" value="' + sId + '" />\n' +
                            '</tr>';

                        $("#table-contract").append(html);

                        $(".deleteContractBtn").click(function () {
                            $(this).parent().parent().remove();
                        });

                        top.layer.close(index);
                    }

                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
</@footer>

