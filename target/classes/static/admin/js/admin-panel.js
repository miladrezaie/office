$(document).ready(function () {
    //car js
    $('#car_modal').on('shown.bs.modal', function () {
        $('#car_modal #name').focus();
    });
    $('.alerting').delay(4000).fadeOut();
    $("form[name='carForm']").validate({
        rules: {
            name: {
                'required': true,
                'minlength': 2
            },
            color: "required",
            plak_number: "required",
            type: "required",
        },
        messages: {
            name: {
                required: "لطفا نام وسیله نقلیه را وارد کنید",
                minlength: "حداقل باید 2 کاراکتر وارد شود"
            },
            // name: "لطفا نام وسیله نقلیه را وارد کنید",
            color: "لطفا رنگ وسیله نقلیه را وارد کنید",
            plak_number: "لطفا شماره پلاک وسیله نقلیه را وارد کنید",
            type: "لطفا نوع وسیله نقلیه را وارد کنید",

        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.tablemy .deleteCar').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'DelCar') {
            $('#tdelModal #tdelRef').attr('href', href);
            $('#tdelModal').modal();
        } else {
            $('#empdelModal #empdelRef').attr('href', href);
            $('#empdelModal').modal();
        }
    });
    $('.newCar, .tablemy .editCar').on('click', function (event) {
        console.log("asdaasssssssssssssssss");
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditCar') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            console.log("asdaasssssssssssssssss");
            $.get(href, function (car) {
                $('.carModal #id').val(car.id);
                $('.carModal #name').val(car.name);
                $('.carModal #color').val(car.color);
                $('.carModal #plak_number').val(car.plak_number);
                $('.carModal #type').val(car.type);
            })
            $('.carModal #saveeditbutton').prop("value", "ویرایش");
            $('.carModal #carFrom').modal('show');
        } else if (text == 'newCar') {

            console.log("asdaasssssssssssssssss");
            $.get(href, function (car, status) {
                // $('.carModal #carFrom').modal('show');
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.carModal #saveeditbutton').prop("value", "افزودن");
                $('.carModal #name').val('');
                $('.carModal #id').val('');
                $('.carModal #color').val('');
                $('.carModal #plak_number').val('');
                $('.carModal #type').val('');
                $('.carModal #carFrom').modal('show');
            });
        }
    });


    //brands js
    $('#brand_modal').on('shown.bs.modal', function () {
        $('#brand_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='brandForm']").validate({
        rules: {
            name: "required"
        },
        messages: {
            name: "لطفا نام برند را وارد کنید"
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newBrands, .tablemy .editBrands').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditBrands') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (brand) {
                $('.brandModal #id').val(brand.id);
                $('.brandModal #name').val(brand.name);
            })
            $('.brandModal #saveeditbutton').prop("value", "ویرایش");
            $('.brandModal #brandLabel').prop("value", "ویرایش");

            $('.brandModal #brandFrom').modal('show');
        } else if (text == 'newBrands') {
            $.get(href, function (brand, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.brandModal #saveeditbutton').prop("value", "افزودن");
                $('.brandModal #brandLabel').prop("value", "افزودن");
                $('.brandModal #name').val('');
                $('.brandModal #id').val('');
                $('.brandModal #brandFrom').modal('show');
            });
        }
    });


    //location js
    $('#location_modal').on('shown.bs.modal', function () {
        $('#location_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='locationForm']").validate({
        rules: {
            name: "required"
        },
        messages: {
            name: "لطفا نام مکان را وارد کنید"
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newLocations, .tablemy .editLocations').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditLocations') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (location) {
                $('.locationModal #id').val(location.id);
                $('.locationModal #name').val(location.name);
            })
            $('.locationModal #saveeditbutton').prop("value", "ویرایش");
            $('.locationModal #locationFrom').modal('show');
        } else if (text == 'newLocations') {
            $.get(href, function (location, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.locationModal #saveeditbutton').prop("value", "افزودن");
                $('.locationModal #name').val('');
                $('.locationModal #id').val('');
                $('.locationModal #locationFrom').modal('show');
            });
        }
    });


    //category js
    $('#category_modal').on('shown.bs.modal', function () {
        $('#category_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='categoryForm']").validate({
        rules: {
            name: "required"
        },
        messages: {
            name: "لطفا نام دسته بندی را وارد کنید"
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newCategories, .tablemy .editCategories').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditCategories') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (category) {
                $('.categoryModal #id').val(category.id);
                $('.categoryModal #name').val(category.name);
            })
            $('.categoryModal #saveeditbutton').prop("value", "ویرایش");
            $('.categoryModal #categoryLabel').prop("value", "ویرایش");

            $('.categoryModal #categoryFrom').modal('show');
        } else if (text == 'newCategories') {
            $.get(href, function (category, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.categoryModal #saveeditbutton').prop("value", "افزودن");
                $('.categoryModal #categoryLabel').prop("value", "افزودن");
                $('.categoryModal #name').val('');
                $('.categoryModal #id').val('');
                $('.categoryModal #categoryFrom').modal('show');
            });
        }
    });


    //jobs js
    $('#job_modal').on('shown.bs.modal', function () {
        $('#job_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='jobForm']").validate({
        rules: {
            name: "required",
            // category: "required"
        },
        messages: {
            name: "لطفا نام عنوان شغلی را وارد کنید",
            // category: "حداقل یک دسته بندی انتخاب کنید"
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newJobs, .tablemy .editJobs').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditJobs') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (job) {
                // console.log("ssssssssssss : "+job.category.id);
                console.log("ssssssssssss : "+job.category);
                $('.jobModal #id').val(job.id);
                $('.jobModal #name').val(job.name);
                $('.jobModal #category').selectpicker("val",job.category.id);
            })
            $('.jobModal #saveeditbutton').prop("value", "ویرایش");
            $('.jobModal #jobFrom').modal('show');
        } else if (text == 'newJobs') {
            $.get(href, function (job, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.jobModal #saveeditbutton').prop("value", "افزودن");
                $('.jobModal #name').val('');
                $('.jobModal #category').selectpicker('val','');
                $('.jobModal #id').val('');
                $('.jobModal #jobFrom').modal('show');
            });
        }
    });



    $('#program_modal').on('shown.bs.modal', function () {
        $('#program_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='programForm']").validate({
        rules: {
            name: "required",
            saat_zabt: "required",
            saat_zabt_end: "required",
            rozhafteh: "required",
            date_end: "required",
            date_begin: "required",
        },
        messages: {
            name: "لطفا نام برنامه را وارد کنید",
            saat_zabt: "لطفا ساعت شروع برنامه را وارد کنید",
            saat_zabt_end: "لطفا ساعت خاتمه برنامه را وارد کنید",
            rozhafteh: "لطفا روز برنامه را وارد کنید",
            date_begin: "لطفا تاریخ شروع برنامه را وارد کنید",
            date_end: "لطفا تاریخ خاتمه برنامه را وارد کنید",
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newPrograms, .tablemy .editPrograms').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditPrograms') {
            $("#saveeditbutton").prop("value", "Save");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (program) {
                $('.programModal #id').val(program.id);
                $('.programModal #name').val(program.name);
                $('.programModal #saat_zabt').val(program.saat_zabt);
                $('.programModal #saat_zabt_end').val(program.saat_zabt_end);
                $('.programModal #rozhafteh').selectpicker('val', program.rozhafteh);
                $('.programModal #date_begin').val(program.date_begin);
                $('.programModal #date_end').val(program.date_end);
            })
            $('.programModal #saveeditbutton').prop("value", "ویرایش");
            $('.programModal #programFrom').modal('show');
        } else if (text == 'newPrograms') {
            $.get(href, function (program, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.programModal #saveeditbutton').prop("value", "افزودن");
                $('.programModal #name').val('');
                $('.programModal #saat_zabt').val('');
                $('.programModal #saat_zabt_end').val('');
                // $('.programModal #rozhafteh').val('');
                // $('.programModal #date_begin').val('');
                // $('.programModal #date_end').val('');
                $('.programModal #rozhafteh').selectpicker('val', '');

                $('.programModal #id').val('');
                $('.programModal #programFrom').modal('show');
            });
        }
    });


    //roles js
    $('#role_modal').on('shown.bs.modal', function () {
        $('#role_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='roleForm']").validate({
        rules: {
            name: "required",
            description: "required",
            authorities: "required",
        },
        messages: {
            name: "لطفا نام نقش را وارد کنید",
            description: "وارد کردن توضیحات الزامی است",
            authorities: "دادن حداقل یک سطح دسترسی به کاربر الزامی است",
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newRoles, .tablemy .editRoles').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditRoles') {
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (role) {

                $('.roleModal #id').val(role.id);
                $('.roleModal #name').val(role.name);
                $('.roleModal #description').val(role.description);
                $('.roleModal #authorities').selectpicker('val', role.authorities);
            })
            $('.roleModal #saveeditbutton').prop("value", "ویرایش");
            $('.roleModal #roleFrom').modal('show');
        } else if (text == 'newRoles') {

            $.get(href, function (role, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.roleModal #saveeditbutton').prop("value", "افزودن");
                $('.roleModal #name').val('');
                $('.roleModal #description').val('');
                $('.roleModal #authorities').selectpicker('val', '');
                $('.roleModal #id').val('');
                $('.roleModal #roleFrom').modal('show');
            });
        }
    });


    //tajhizat js
    $('#tajhizat_modal').on('shown.bs.modal', function () {
        $('#tajhizat_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='tajhizatForm']").validate({
        rules: {
            name: "required",
            amvalid: "required",
            serial_id: "required",
            model: "required",
            location: "required",
            brand: "required",
            type: "required",
        },
        messages: {
            name: "لطفا نام تجهیز را وارد کنید",
            amvalid: "وارد کردن شماره اموال الزامی است",
            serial_id: "وارد کردن شماره سریال الزامی است",
            model: "وارد کردن مدل تجهیز الزامی است",
            location: "وارد کردن مکان قرار گیری تجهیز الزامی است",
            brand: "وارد کردن برند تجهیز الزامی است",
            type: "وارد کردن نوع تجهیز الزامی است",

        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newTajhizats, .tablemy .editTajhizats').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditTajhizats') {
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (tajhizat) {
                console.log(tajhizat.brand);
                $('.tajhizatModal #id').val(tajhizat.id);
                $('.tajhizatModal #name').val(tajhizat.name);
                $('.tajhizatModal #amvalid').val(tajhizat.amvalid);
                // $('.tajhizatModal #type').val(tajhizat.type);
                $('.tajhizatModal #serial_id').val(tajhizat.serial_id);
                $('.tajhizatModal #model').val(tajhizat.model);
                $('.tajhizatModal #location').selectpicker('val', tajhizat.location.id);
                $('.tajhizatModal #brand').selectpicker('val', tajhizat.brand.id);
                $('.tajhizatModal #type').selectpicker('val', tajhizat.type);
                $('.tajhizatModal #image').attr('src', "data:img/jpg;base64," + tajhizat.img);
                // $('.tajhizatModal #brand').val(tajhizat.brand);
            })
            $('.tajhizatModal #saveeditbutton').prop("value", "ویرایش");
            $('.tajhizatModal #tajhizatFrom').modal('show');
        } else if (text == 'newTajhizats') {
            $.get(href, function (tajhizat, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.tajhizatModal #saveeditbutton').prop("value", "افزودن");
                $('.tajhizatModal #name').val('');
                $('.tajhizatModal #amvalid').val('');
                $('.tajhizatModal #location').selectpicker('val', '');
                $('.tajhizatModal #brand').selectpicker('val', '');
                $('.tajhizatModal #type').selectpicker('val', '');
                $('.tajhizatModal #serial_id').val('');
                $('.tajhizatModal #model').val('');
                $(".tajhizatModal #image").removeAttr("src");
                $('.tajhizatModal #id').val('');
                $('.tajhizatModal #tajhizatFrom').modal('show');
            });
        }
    });


    //users js
    $('#user_modal').on('shown.bs.modal', function () {
        $('#user_modal #name').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    $("form[name='userForm']").validate({
        rules: {
            personalId: "required",
            FName: "required",
            Lname: "required",
            job: "required",
            roles: "required",
            pass: "required",
            file: "required",
            category: "required",
        },
        messages: {
            personalId: "لطفا نام نقش را وارد کنید",
            FName: "وارد کردن نام الزامی است",
            Lname: "وارد کردن نام خانوادگی الزامی است",
            job: "وارد کردن عنوان شغلی الزامی است",
            roles: "وارد کردن سطح دسترسی الزامی است",
            pass: "وارد کردن رمز عبور الزامی است",
            file: "وارد کردن فایل الزامی است",
            category: "وارد کردن دسته بندی الزامی است",
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.newUsers, .tablemy .editUsers').on('click', function (event) {
        event.preventDefault();
        console.log("userrrrrrrrrrrrrrrrrrrrrr");

        var href = $(this).attr('href');
        var text = $(this).attr('id');
        if (text == 'EditUsers') {

            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (user) {
                console.log("userrrrrrrrrrrrrrrrrrrrrr edit");
                $('.userModal #id').val(user.id);
                $('.userModal #personalId').val(user.personalId);
                $('.userModal #FName').val(user.fname);
                $('.userModal #Lname').val(user.lname);
                $('.userModal #job').selectpicker('val', user.job.id);
                $('.userModal #roles').selectpicker('val', user.roles[0].id);
                // $('.userModal #category').selectpicker('val', user.category.id);
                // $('.userModal #image_emza').attr('src', "data:img/jpg;base64," + user.);

            })
            $('.userModal #saveeditbutton').prop("value", "ویرایش");
            $('.userModal #userFrom').modal('show');
        } else if (text == 'newUsers') {
            $.get(href, function (user, status) {
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.userModal #saveeditbutton').prop("value", "افزودن");
                $('.userModal #id').val('');
                $('.userModal #personalId').val('');
                $('.userModal #FName').val('');
                $('.userModal #Lname').val('');
                $('.userModal #DMac').val('');
                $('.userModal #job').selectpicker('val', '');
                $('.userModal #roles').selectpicker('val', '');
                // $('.userModal #category').selectpicker('val', '');
                $('.userModal #userFrom').modal('show');
            });
        }
    });



    //office js
    $('.ch').on('change', function (event) {
        event.preventDefault();
        console.log("event : " +$(this).val());

        var value = $('#job_form').val();
        console.log("salam" + value);

        // console.log("salam" + event.ip);
        $.getJSON('/findbyjob/' + value, function (data) {

            console.log("****************** data :" + data);
            console.log("****************** data :" + data.ip);
            console.log("milad shorooooooooooooooooooooooooooooooo");
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                console.log(data[i].fullname);
                gencheckboxes = gencheckboxes + '<input type="checkbox" name="users" value="' + data[i].id + '">' + data[i].fullname + '<br></input>';
            }
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

        $.getJSON('http://localhost:8085/findbyjob/' + value, function (data) {

            console.log("****************** data :" + data);
            console.log("****************** data :" + data.ip);
            console.log("milad shorooooooooooooooooooooooooooooooo");
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                console.log(data[i].fullname);
                gencheckboxes = gencheckboxes + '<input type="checkbox" name="users" value="' + data[i].id + '">' + data[i].fullname + '<br></input>';
            }
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

    });
    $('.changeCategory').on('change', function (event) {
        event.preventDefault();
        var value = $('#category_form').val();
        $.getJSON('/findbycategory/' + value, function (data) {
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                gencheckboxes = gencheckboxes + '<ul class="mr-4"><li>'+data[i].name+'<ul className="mr-4">';
                for (let j = 0; j < data[i].users.length; j++) {
                    gencheckboxes +='<li><input type="checkbox" name="users" value="' + data[i].users[j].id + '">' + data[i].users[j].personalId + " - "+data[i].users[j].fullname + '<br></input></li>';
                }
                gencheckboxes+='</ul></li></ul>'
            }
            gencheckboxes+='<input type="submit" class="btn btn-primary" value="save"/>';
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

        $.getJSON('http://localhost:8085/findbycategory/' + value, function (data) {
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                gencheckboxes = gencheckboxes + '<ul class="mr-4"><li>'+data[i].name+'<ul className="mr-4">';
                for (let j = 0; j < data[i].users.length; j++) {
                    gencheckboxes +='<li><input type="checkbox" name="users" value="' + data[i].users[j].id + '">' + data[i].users[j].personalId + " - "+data[i].users[j].fullname + '<br></input></li>';
                }
                gencheckboxes+='</ul></li></ul>';
            }
            gencheckboxes+='<input type="submit" class="btn btn-primary" value="save"/>';
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

    });
    $('#of_modal').on('shown.bs.modal', function () {
        $('#of_modal #program').focus();
    });
    $('.alerting').delay(2000).fadeOut();
    // $('#role_modal #name').focus();
    $("form[name='off_form_form']").validate({
        rules: {
            tahayekonande: "required",
            program: "required",
            date_begin: "required",
            date_end: "required",
            saat_zabt: "required",
            saat_zabt_end: "required",
            location: "required",
            type: "required",
        },
        messages: {
            tahayekonande: "لطفا نام نقش را وارد کنید",
            program: "وارد کردن نام برنامه الزامی است",
            date_begin: "وارد کردن تاریخ روز آفیش الزامی است",
            date_end: "وارد کردن تارخ اتمام آفیش الزامی است",
            location: "وارد کردن مکان آفیش الزامی است",
            type: "وارد کردن نوع آفیش الزامی است",
            saat_zabt: "وارد کردن ساعت شروع ضبط آفیش الزامی است",
            saat_zabt_end: "وارد کردن ساعت اتمام ضبط آفیش الزامی است",
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
    $('.mnBtn, .tmnBtn, .table .meBtn, .empBtn, .table .empeBtn, .table .teBtn, .table .feBtn, .fnBtn').on('click', function (event) {

        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');

         if (text == 'OpenForm') {
            $('.omyForm #fid').val(href);
            $('.omyForm #oexampleModal').modal();
        }  else if (text == 'newoffice') {
             $('.fmyForm #saveeditbutton').prop("value", "ثبت");
            $('.fmyForm #fexampleModal').modal();

        } else if (text =='editOffice'){
             $('.fmyForm #saveeditbutton').prop("value", "تغییر");
             $('.fmyForm #fexampleModal').modal();
         }
    });


    $('.table_office .newOffices,.table_office .editOffices,.table_office .addTajhizToOfficeAndUser').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).attr('id');

        if (text == 'EditOffices') {
            console.log("edit office");
            $("#saveeditbutton").prop("value", "ذخیره");
            $("label.error").hide();
            $(".error").removeClass("error");
            $.get(href, function (office) {
                console.log(office.id);
                console.log();
                var list =[];
                for (var k =0 ; k<office.tajhizatss.length ; k++){
                    console.log(office.tajhizatss[k].id);
                    list[k]=office.tajhizatss[k].id;
                }
                console.log(list);
                $('.officeModal #id').val(office.id);
                $('.officeModal #tajhizatss').selectpicker('val',list );
            });
            $('.officeModal #saveeditbutton').prop("value", "ویرایش");
            $('.officeModal #officeFrom').modal('show');
        } else if (text == 'newOffices') {
            console.log("new office");
            $.get(href, function (office, status) {
                console.log(office);
                $("label.error").hide();
                $(".error").removeClass("error");
                $('.officeModal #saveeditbutton').prop("value", "افزودن");
                // $('.officeModal #name').val('');
                // $('.officeModal #id').val('');
                $('.officeModal #tajhizats').selectpicker('val', '');
                $('.officeModal #officeFrom').modal('show');
            });
        }
        else if (text == 'addTajhizToOfficeAndUser') {
            console.log("addTajhizToOfficeAndUser");
            $("label.error").hide();
            $(".error").removeClass("error");
            $('.officeModal #saveeditbutton_').prop("value", "افزودن");
            $.get(href, function (office) {
                console.log(office.tajhizatss);
                $('.officeModal #users').val('');
                $('.officeModal #id').val(office.id);
                var list =[];
                for (var k =0 ; k<office.tajhizatss.length ; k++){
                    console.log(office.tajhizatss[k].id);
                    list[k]=office.tajhizatss[k].id;
                }
                $('.officeModal #tajhizatsss').selectpicker('val',list );
                $('.officeModal #officeFrom_').modal('show');
            });
        }
    });

    $(".office-date").persianDatepicker({
        observer: true,
        format: 'YYYY/MM/DD',
        autoClose: true,
    });
    $(".office-date-time").persianDatepicker({
        observer: true,
        format: 'HH:mm',
        altField: '.observer-example-alt',
        onlyTimePicker: true,
        autoClose: true,
    });
    $(".program-date").persianDatepicker({
        observer: true,
        format: 'YYYY/MM/DD',
        autoClose: true,
    });
    $(".program-date-time").persianDatepicker({
        observer: true,
        format: 'HH:mm',
        altField: '.observer-example-alt',
        onlyTimePicker: true,
        autoClose: true,
    });
});