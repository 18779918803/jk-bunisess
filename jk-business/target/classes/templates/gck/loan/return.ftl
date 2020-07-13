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
                                            <span class="form-required">*</span>协议编号：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" id="number"  value="${loan.number!}"
                                                       class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>归还时间：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="tradeTime"
                                                       id="returnTime"
                                                       value="${(loan.returnTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required onchange="change()">
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
                                        <label for="shouldAmount" class="control-label col-sm-2">
                                            <span class="form-required">*</span>应还本金：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="shouldAmount" name="shouldAmount" value="${shouldAmount!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                             </div>



                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="shouldRate" class="control-label col-sm-2">
                                            <span class="form-required">*</span>应还利息：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="shouldRate" name="shouldRate" value="${shouldRate!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="amount" class="control-label col-sm-2">
                                            <span class="form-required">*</span>实还本金：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="amount" name="amount"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="rate" class="control-label col-sm-2">
                                            <span class="form-required">*</span>实还利息：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="rate" name="rate"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <!-- 隐藏域 -->
                                <input type="hidden" name="loanId" id="loanId" value="${loan.id!}">
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
     /*   $("#returnTime").change(function (e) {
            var  time= $("#returnTime").val();
            alert(time);
        });*/
        function change(){
            var  time= $("#returnTime").val();
            var  loanId= $("#loanId").val();
            $.ajax({
                type: "GET",
                url: "${basePath}/gck/loan/getShouldAmount",
                dataType: 'json',
                data: {"returnTime": time,"id":loanId},
                success: function (data) {
                    var  principal= data.data.principal;
                    var  rate=data.data.rate;
                    $("#shouldAmount").val(principal);
                    $("#shouldRate").val(rate);
                }
            });

        }

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/gck/loanReturn/insert",
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

