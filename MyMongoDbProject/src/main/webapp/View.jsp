<%@	page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import ="org.apache.catalina.core.ApplicationContext"
    import ="org.springframework.context.support.GenericXmlApplicationContext"
    import = "com.ktds.dao.*"
    import ="java.util.List"%>  
<%
// 몽고디비 연결부분

String url = "mongoContext.xml";

GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(url);

MongoDAO dao = ctx.getBean("mongoDAO", MongoDAO.class);
if(dao == null){
    System.out.println("db 연결 실패!!");
}
else{
    System.out.println("db 연결 성공!!");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>데이터 베이스 게시판</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>

<script type="text/javascript">

//jquery 부분
//document.ready (jquery 는 html 이 컴파일 되기 전에 먼저 읽혀지지만, 이 ready 함수를 이용하면 html 이 생성된 이후에 읽혀지게 할 수있다.)
$(document).ready(function() {
	// 페이지 생성 알고리즘
	var dataNum = <%=dao.getDataSize()%>;
	var pageNum = <%=dao.getPageNum()%>;
	var pageByPageNum = <%=dao.getPageByPageNum()%>;
	// i 는 이동하는 페이지 넘버들의 페이지 수를 의미  i 가 1이면  페이지쪽수는 1 페이지부터 10 페이지까지를 의미함	
	var presentPageByPageNum = 1;
		/* 예를 들어서 데이터가 56 개이고 페이지가 6 페이지 라면 첫 페이지에는 페이지 수가 열개가 초과되면 다 생성후 다른페이지로 넘어가는
		버튼을 누르면 생성할 수 있게 하였고*/
	
		$('#pagingNumSpace').append($('<button/>', {
			id : 'BeforePage',
			value : 'before',
			text : 'before'
		}));	
	for(var i = 1; i<=pageByPageNum; i++ ) {
		if( (i * 10) >= pageNum ) {
			for(var k = ( (i - 1) * 10 + 1); k <= pageNum; k ++) {
				$('#pagingNumSpace').append($('<button/>', {
					id : 'page'+k,
					value : k,
					text : k
				}));
			}
		}
		else {
			for( var j  =(i - 1) * 10 + 1 ;  j <= i * 10 ; j++ ) {
				$('#pagingNumSpace').append($('<button/>', {
					id : 'page'+j,
					value : j,
					text : j
				}));
			}
		}
	}

	for(var l = 11; l <= pageNum; l++) {
		$('#page'+l).hide();
	}
	$('#pagingNumSpace').append($('<button/>', {
		id : 'afterPage',
		value : 'next',
		text : 'next'
	}));
	
	// 다음 페이지버튼을 눌렀을 때 페이지를 보여주고  (여기에 없으면 조건을 추가해야 한다. 이미 추가되어있으면 다시 동작할 필요가 없음)
	$('#afterPage').click(function() {
		if((presentPageByPageNum+1) > pageByPageNum) {
			alert("더 이상의 페이지는 없습니다.");
		}
		else if((presentPageByPageNum+1) === pageByPageNum) {
			presentPageByPageNum = presentPageByPageNum+1;
			for(var m = (presentPageByPageNum -2) * 10 + 1; m <= (presentPageByPageNum -1) * 10 + 10; m++) {
				$('#page'+m).hide();
			}
			for(var n = (presentPageByPageNum -1) * 10 + 1; n <= pageNum; n++) {
				$('#page'+n).show();
			}   
		}
		else{
			presentPageByPageNum = presentPageByPageNum+1;
			for(var m = (presentPageByPageNum -2) * 10 + 1; m <= (presentPageByPageNum -1) * 10 + 10; m++) {
				$('#page'+m).hide();
			}
			for(var n = (presentPageByPageNum -1) * 10 + 1; n <= (presentPageByPageNum -1) * 10 + 10; n++) {
				$('#page'+n).show();
			}   
		}
	});
	
	$('#BeforePage').click(function() {
		if((presentPageByPageNum-1) === 0) {
			alert("더 이상의 페이지는 없습니다.");
		}
		else{
			presentPageByPageNum = presentPageByPageNum-1;
			for(var m = ( presentPageByPageNum ) * 10 + 1; m <= (presentPageByPageNum ) * 10 + 10; m++) {
				$('#page'+m).hide();
			}
			for(var n = (presentPageByPageNum -1) * 10 + 1; n <= (presentPageByPageNum -1) * 10 + 10; n++) {
				$('#page'+n).show();
			}   
		}
	});	
	// 페이지 알고리즘 끝
	var a;
	// 페이지 누를때마다 데이터를 집어넣는 알고리즘ㄴ
	<%for(int i = 1; i<dao.getPageNum(); i++ ) {
		List<MongoDTOstudent> studentList = dao.tenStudentDivByPageNum(i);%>
		$('#page'+<%=i%>).click(function() {
			<%for(int g = 1; g<=10; g++) {%>
				$('#id'+<%=g%>).text(''+<%=studentList.get(g-1).getId()%>);
				$('#name'+<%=g%>).text('<%=studentList.get(g-1).getName()%>');
				$('#studentNum'+<%=g%>).text(''+<%=studentList.get(g-1).getStudentNum()%>);
			<%}%>
		});
	<%}
	List<MongoDTOstudent> studentList2 = dao.tenStudentDivByPageNum(dao.getPageNum());
	%>
	$('page'+<%=dao.getPageNum()%>).click(function() {
		<%for(int f = 1; f<= dao.getPageNum() * 10 - dao.getDataSize(); f++) {%>
			$('#id'+<%=f%>).text(''+<%=studentList2.get(f-1).getId()%>);
			$('#name'+<%=f%>).text('<%=studentList2.get(f-1).getName()%>');
			$('#studentNum'+<%=f%>).text(''+<%=studentList2.get(f-1).getStudentNum()%>);
		<%}%>
	});
});

</script>
</head>

<body>

<h1>데이터 베이스 정보 </h1><br><br>

<form>
특정 정보 찾기 <br><br>

순번 : <input type="text" name = "id" id = "id"></input> <br>
이름 : <input type="text" name = "name" id = "name"></input> <br>
학번 : <input type="text" name = "studentNum" id = "studentNum"></input> <br><br>
<input type="button" id = "submitButton" value="찾기"></input>
</form>

<table id = "viewTable">
<tr>
<th>순번</th><th>이름</th><th>학번</th>
</tr>
<tr>
<td id = "id1"></td><td id = "name1"></td><td id = "studentNum1"></td>
</tr>
<tr>
<td id = "id2"></td><td id = "name2"></td><td id = "studentNum2"></td>
</tr>
<tr>
<td id = "id3"></td><td id = "name3"></td><td id = "studentNum3"></td>
</tr>
<tr>
<td id = "id4"></td><td id = "name4"></td><td id = "studentNum4"></td>
</tr>
<tr>
<td id = "id5"></td><td id = "name5"></td><td id = "studentNum5"></td>
</tr>
<tr>
<td id = "id6"></td><td id = "name6"></td><td id = "studentNum6"></td>
</tr>
<tr>
<td id = "id7"></td><td id = "name7"></td><td id = "studentNum7"></td>
</tr>
<tr>
<td id = "id8"></td><td id = "name8"></td><td id = "studentNum8"></td>
</tr>
<tr>
<td id = "id9"></td><td id = "name9"></td><td id = "studentNum9"></td>
</tr>
<tr>
<td id = "id10"></td><td id = "name10"></td><td id = "studentNum10"></td>
</tr>
</table>

<div id = "pagingNumSpace">
</div>

</body> 
</html>

<% } %>