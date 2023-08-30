function update(userId, event) {
	event.preventDefault(); //폼태그 액션 막기
	
	let data = $("#profileUpdate").serialize();
	
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res=>{
		console.log("성공", res); //HttpStatus 상태코드가 200번대면 성공이 뜨고
		location.href=`/user/${userId}`;
	}).fail(error=>{
		console.log(error);
		if(error.data==null){
			alert(error.responseJSON.messasge);
		}else{			
			alert(JSON.stringify(error.responseJSON.data)); //HttpStatus 상태코드가 200번대가 아니면 실패	
		}
	});
	
	
}