function delfruit(fid){
    if(confirm("是否确认删除？")){
        window.location.href='fruit.do?fid='+fid+'&operate=del';
    }
}

//confirm 对话框


function page(pageNo) {
    window.location.href='fruit.do?pageNo='+pageNo;


}

