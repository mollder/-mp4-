<%@	page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import ="org.apache.catalina.core.ApplicationContext"
    import ="org.springframework.context.support.GenericXmlApplicationContext"
    import = "com.ktds.dao.*"
    import ="java.util.List"%>  
<%
// ������ ����κ�

String url = "mongoContext.xml";

GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(url);

MongoDAO dao = ctx.getBean("mongoDAO", MongoDAO.class);
if(dao == null){
    System.out.println("db ���� ����!!");
}
else{
    System.out.println("db ���� ����!!");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>������ ���̽� �Խ���</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>

<script type="text/javascript">

//jquery �κ�
//document.ready (jquery �� html �� ������ �Ǳ� ���� ���� ����������, �� ready �Լ��� �̿��ϸ� html �� ������ ���Ŀ� �������� �� ���ִ�.)
$(document).ready(function() {
	// ������ ���� �˰���
	var dataNum = <%=dao.getDataSize()%>;
	var pageNum = <%=dao.getPageNum()%>;
	var pageByPageNum = <%=dao.getPageByPageNum()%>;
	// i �� �̵��ϴ� ������ �ѹ����� ������ ���� �ǹ�  i �� 1�̸�  �������ʼ��� 1 ���������� 10 ������������ �ǹ���	
	var presentPageByPageNum = 1;
		/* ���� �� �����Ͱ� 56 ���̰� �������� 6 ������ ��� ù ���������� ������ ���� ������ �ʰ��Ǹ� �� ������ �ٸ��������� �Ѿ��
		��ư�� ������ ������ �� �ְ� �Ͽ���*/
	
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
	
	// ���� ��������ư�� ������ �� �������� �����ְ�  (���⿡ ������ ������ �߰��ؾ� �Ѵ�. �̹� �߰��Ǿ������� �ٽ� ������ �ʿ䰡 ����)
	$('#afterPage').click(function() {
		if((presentPageByPageNum+1) > pageByPageNum) {
			alert("�� �̻��� �������� �����ϴ�.");
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
			alert("�� �̻��� �������� �����ϴ�.");
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
	// ������ �˰��� ��
	var a;
	// ������ ���������� �����͸� ����ִ� �˰���
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

<h1>������ ���̽� ���� </h1><br><br>

<form>
Ư�� ���� ã�� <br><br>

���� : <input type="text" name = "id" id = "id"></input> <br>
�̸� : <input type="text" name = "name" id = "name"></input> <br>
�й� : <input type="text" name = "studentNum" id = "studentNum"></input> <br><br>
<input type="button" id = "submitButton" value="ã��"></input>
</form>

<table id = "viewTable">
<tr>
<th>����</th><th>�̸�</th><th>�й�</th>
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