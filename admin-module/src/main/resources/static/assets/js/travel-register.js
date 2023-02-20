const picker = $('.cus-datepicker');
const tbody = $('#acc-tbody');
const pdfUpload = $('#pdf-upload');

$(document).ready(function () {
    picker.dateDropper();
    $('.pick-submit').on('click', check_date_duration)
    picker.on('click', function () {
        $(".pick-submit").attr("data-id", $(this).data("id"))
    })
});


$('#upload-btn').on('click', function () {
    $('#pdf-upload').click();
})
$('#add-address-btn').on('click', addAccommodation);
$('#complete-register').on('click', completeTravelInfo)
pdfUpload.change(function (e) {
    $('#display-file-name').val(this.files[0].name)
})
tbody.on('click', '.remove-btn', removeAccTr);


function completeTravelInfo() {
    let data = new FormData();
    let docFile = pdfUpload[0].files;

    let startDate = picker.eq(0).val().split('/');
    let endDate = picker.eq(1).val().split('/');

    let obj = $('#travel-main').serializeObject();
    obj["startDate"] = `${startDate[2]}-${startDate[0]}-${startDate[1]}`;
    obj["endDate"] = `${endDate[2]}-${endDate[0]}-${endDate[1]}`;

    let objJsonStr = JSON.stringify(obj);
    if (check_empty_input(obj)) {

        // 여행 등록
        if (pdfUpload.val()) {
            data.append("files", docFile[0])
        }

        data.append('travelInfo', new Blob([objJsonStr], {type: 'application/json'}));

        //숙소 등록
        let acc_infos = tbody.children();
        let acc_datas = [];
        if (startDate !== endDate && acc_infos.length !== 0) {
            $.each(acc_infos, function (idx, info) {
               acc_datas.push($(info).data('acc-tr'))
            })
            let infoJsonStr = JSON.stringify(acc_datas);
            data.append("accommodationInfos", new Blob([infoJsonStr], {type: 'application/json'}))
        }

        $.ajax({
            type: "POST",
            url: "/travel-register",
            processData: false,
            contentType: false,
            data: data,
            success: function (res) {
                console.log(res.status)
                location.replace("/");
            },
            err: function (err) {
                console.log("err:", err)
            }
        })
    }
}

function addAccommodation() {
    let accInfo = $('#acc-info').serializeObject();
    let accAddress = $('#acc-address').serializeObject();

    let address = $('#address');
    let accAll = Object.assign(accInfo, accAddress);
    if (check_empty_input(accAll)) {
        resetInputBorder(accAll);
    } else return;

    accAll['posX'] = address.data("pos-x");
    accAll['posY'] = address.data("pos-y");
    accAll['mapLink'] = address.data("maplink");

    drawAddress(accAll);
    clearInput(accAll);

}

function removeAccTr() {
    $(this).parent().parent().parent().remove();
}

function drawAddress(obj) {
    const tr = tbody.find(`#acc-${obj.accDate}`);

    if (tr.length > 0) {
        let isChange = confirm("해당 날짜에 이미 숙소가 있습니다. 교체 하시겠습니까?");
        if (isChange) {
            tr.remove();
        } else return;
    }
    let obj_json = JSON.stringify(obj)

    let html = ` <tr id="acc-${obj.accDate}" data-acc-tr= '${obj_json}' >
            <td>${obj.accDate}</td>
            <td>${obj.address} ${obj.extraAddress !== "" ? obj.extraAddress : ""} ${obj.detailAddress}</td>
            <td>${obj.checkIn}</td>
            <td>${obj.checkOut}</td>
            <td>${obj.price}</td>
            <td>${obj.remark}</td>
            <td><button type="button" class="btn-close text-dark py-0 opacity-10 remove-btn"><span aria-hidden="true">&times;</span></button></td>
        </tr>`

    tbody.append(html);
}

let checked_start = false;

function check_date_duration() {

    if (!checked_start) {
        checked_start = true;
        return;
    }

    let id = $(this).attr("data-id");

    let inputIdx = id.split("-")[1];
    let from = picker.eq(inputIdx - 1).val();
    let to = picker.eq(inputIdx).val();

    if (+inputIdx === 0) {
        from = picker.eq(inputIdx).val();
        to = picker.eq(inputIdx + 1).val();
    }

    let fromDate = from.split("/");
    let toDate = to.split("/");

    let start = new Date(`${fromDate[2]}-${fromDate[0]}-${fromDate[1]}`);
    let end = new Date(`${toDate[2]}-${toDate[0]}-${toDate[1]}`);


    if (end < start) {

        const today = new Date();
        const year = today.getFullYear();
        const month = today.getMonth() + 1;
        const date = today.getDate();

        if (+inputIdx === 1) {
            alert("시작 일 보다 빠른 날짜 입니다.");
        } else if (+inputIdx === 0) {
            alert("종료 일 보다 늦은 날짜 입니다.");
        }
        picker.eq(inputIdx).val(`${month >= 10 ? month : '0' + month}/${date >= 10 ? date : '0' + date}/${year}`)
    }


}

function check_empty_input(formObj) {

    let empty_input = [];
    let isPass = true;
    for (let key in formObj) {
        if (key === 'remark') continue;
        if (key === 'extraAddress') continue;
        if (!formObj[key]) {
            empty_input.push(key);
        } else {
            $(`input[name=${key}]`).css("border", "1px solid #d2d6da");
        }
    }
    if (empty_input.length !== 0) {
        empty_input.forEach((emptyKey) => {
            $(`input[name=${emptyKey}]`).css("borderColor", "red");
        })
        isPass = false;
    }

    return isPass;

}

function clearInput(formObj) {
    for (let key in formObj) {
        $(`input[name=${key}]`).val('');
    }
}

function resetInputBorder(formObj) {

    for (let key in formObj) {
        if (key === 'remark') continue;
        if (key === 'extraAddress') continue;
        if (!formObj[key]) {
            $(`input[name=${key}]`).css("border", "1px solid #d2d6da");
        }
    }
}