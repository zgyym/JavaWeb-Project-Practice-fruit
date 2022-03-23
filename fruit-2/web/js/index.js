function delfruit(fid){
    if(confirm("是否确认删除？")){
        window.location.href='del.do?fid='+fid;
    }
}

//confirm 对话框


function page(pageNo) {
    window.location.href='index?pageNo='+pageNo;
}