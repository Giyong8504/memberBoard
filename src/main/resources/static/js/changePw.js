function showConfirmation() {

    var isConfirmed = confirm("비밀번호를 변경하시겠습니까?");

    if (isConfirmed) {
        alert('변경 되었습니다.');
    }
    return isConfirmed;
}