<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Home Page</title>
<script type="text/javascript">
	function cancelButton() {
		let button = document.getElementById('cnclbtn');
		button.onclick = function(e) {
			location.href = '/user';
		}

	}
	function disabledDeletebutton() {
		document.getElementById('dltbtn').disabled = true;
		document.getElementById("dltbtn").style.background = "#bce8f7";
	}
	function onDelete() {
		var checkBoxes = document
				.querySelectorAll('input[type="checkbox"]:checked').length;
		if (checkBoxes > 0) {
			document.getElementById('dltbtn').disabled = false;
			document.getElementById("dltbtn").style.background = "deepskyblue";
			return true;
		} else {
			document.getElementById('dltbtn').disabled = true;
			document.getElementById("dltbtn").style.background = "#bce8f7";
			return false;
		}
	}
	function ValidateTextBox() {
		let x = document.getElementById("a1").value;
		let validate = true;
		let text = "";
		if (x == "") {
			text = text + "FirstName ";
			validate = false;
		}
		let y = document.getElementById("a2").value;
		if (y == "") {
			text = text + "LastName ";
			validate = false;
		}
		let z = document.getElementById("a3").value;
		if (z == "") {
			text = text + "Age ";
			validate = false;
		} else if (z > 0 && z < 120) {
			document.getElementById("demo2").innerHTML = "";
		} else {
			document.getElementById("demo2").innerHTML = "Enter Valid Age";
			validate = false;
		}
		let p = document.getElementById("a4").value;
		if (p == "") {
			text = text + "Email";
			validate = false;
		} else {
			var validRegex = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+$";
			if (p.match(validRegex)) {
				document.getElementById("demo3").innerHTML = "";
			} else {
				//text = "Enter Valid Email";
				document.getElementById("demo3").innerHTML = "Enter Valid Email";
				validate = false;
			}
		}
		let q = document.getElementById("a5").value;
		if (q == "") {
			text = text + " Occupation ";
			validate = false;
		}
		if (text != "") {
			document.getElementById("Error").innerHTML = "Please Enter " + text;
		}
		return validate;
	}
</script>
<style type="text/css">
table, th, td {
	border-collapse: collapse;
}

#UserTable tr:nth-of-type(even) {
	background-color: aliceblue;
}

.btndlt {
	width: 70%;
	text-align: -webkit-right;
}

.Delete-btn {
	width: 10%;
	height: 30px;
	border-radius: 50px;
	border-color: dodgerblue;
	font-weight: bold;
	color: white;
}

.report-tab {
	width: 70%;
	font-weight: bold;
}

.userinfo-tab, .updateinfo-tab {
	width: 25%;
	font-weight: bold;
}

.add-btn, .udt-btn {
	width: 100%;
	height: 30px;
	border-radius: 50px;
	background-color: deepskyblue;
	border-color: dodgerblue;
	font-weight: bold;
	color: white;
}

.rst-btn, .cncl-btn {
	width: 50%;
	height: 30px;
	border-radius: 50px;
	background-color: deepskyblue;
	border-color: dodgerblue;
	font-weight: bold;
	color: white;
}

.rpt-tab-hd {
	border: white;
	background-color: lightskyblue;
}

.fld-set {
	width: 400px;
	border-color: deepskyblue;
	border-width: 4px;
}

#a1, #a2, #a3, #a4, #a5 {
	/* 	width: 97%; */
	height: 20px;
	border-radius: 5px;
	border-color: white;
}
</style>
</head>
<body style="background-color: #bce8f7;"
	onload="return disabledDeletebutton()">
	<div align="center" class="updatebox">
		<c:if test="${update eq true}">
			<form id="updateuserinfo" action="updateUser/${id}" method="post">
				<fieldset class="fld-set">
					<legend>
						<h1 style="color: darkblue">Update User Form</h1>
					</legend>
					<table class="updateinfo-tab">
						<tr>
							<td>ID</td>
							<td><input type="text" value="${user.id}" name="id" id="a0"
								readonly="readonly"><br></td>
						</tr>
						<tr>
							<td>First Name</td>
							<td><input type="text" value="${user.firstName}"
								name="firstName" id="a1"></td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td><input type="text" value="${user.lastName}"
								name="lastName" id="a2"></td>
						</tr>
						<tr>
							<td>Age</td>
							<td><input type="number" value="${user.age}" name="age"
								id="a3"></td>
						</tr>
						<p id="demo2" style="color: red; font-weight: bold;"></p>
						<tr>
							<td>Email</td>
							<td><input type="email" value="${user.email}" name="email"
								id="a4"></td>
						</tr>
						<p id="demo3" style="color: red; font-weight: bold;"></p>
						<tr>
							<td>Occupation</td>
							<td><input type="text" value="${user.occupation}"
								name="occupation" id="a5">
								<p id="demo4"></p></td>
						</tr>
						<tr>
							<td><button type="submit" class="udt-btn" id="updatebtn1"
									onclick="return ValidateTextBox()" title="Click to Update User">UPDATE</button></td>
							<p id="Error" style="color: red; font-weight: bold;"></p>
							<td style="text-align: center;"><button type="submit"
									id="cnclbtn" class="cncl-btn" onclick="cancelButton()"
									title="Click to Cancel Update">CANCEL</button></td>
						</tr>
					</table>
			</form>
			</fieldset>
		</c:if>
	</div>
	<div class="formbox" align="center">
		<div style="color: darkblue; font-weight: bold;">
			<c:out value="${updateMessage}"></c:out>
		</div>
		<div style="color: darkblue; font-weight: bold;">
			<c:out value="${deleteMessage}"></c:out>
		</div>
		<c:if test="${update eq false}">
			<form id="userinfo" action="adduser" method="post">
				<fieldset class="fld-set">
					<legend>
						<h1 style="color: darkblue;">New User Form</h1>
					</legend>
					<table class="userinfo-tab">
						<tr>
							<td>First Name</td>
							<td><input type="text" name="firstName" id="a1"
								placeholder=" FirstName"></td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td><input type="text" name="lastName" id="a2"
								placeholder=" LastName"></td>
						</tr>
						<tr>
							<td>Age</td>
							<td><input type="number" name="age" id="a3"
								placeholder=" 0 > Age < 120 "></td>
						</tr>
						<p id="demo2" style="color: red; font-weight: bold;"></p>
						<tr>
							<td>Email</td>
							<td><input type="email" name="email" id="a4"
								placeholder=" abc@gmail.com"></td>
						</tr>
						<p id="demo3" style="color: red; font-weight: bold;"></p>
						<tr>
							<td>Occupation</td>
							<td><input type="text" name="occupation" id="a5"
								placeholder=" Occupation"><p id="demo4"></p></td>
						</tr>
						<tr>
							<td><button type="submit" class="add-btn"
									onclick="return ValidateTextBox()" title="Click to Add User">ADD</button></td>
							<p id="Error" style="color: red; font-weight: bold;"></p>
							<td><div align="center">
									<button type="reset" class="rst-btn" title="click to Reset">RESET</button>
								</div></td>
						</tr>
					</table>
			</form>
			</fieldset>
	</div>
	</c:if>
	<br>
	<div align="center">
		<form action="<%=request.getContextPath()%>deleteUserData"
			method="post">
			<h1 style="color: darkblue;">User Report</h1>
			<div align="right" class="btndlt">
				<button type="submit" value="Delete" id="dltbtn" class="Delete-btn"
					onclick="alert('Do you want to delete this User?');"
					title="Click to Delete User">Delete</button>
			</div>
			<br>
			<table border="2" id="UserTable" class="report-tab">
				<thead class="rpt-tab-hd">
					<tr>
						<th scope="col">Delete</th>
						<th scope="col">ID</th>
						<th scope="col">FirstName</th>
						<th scope="col">LastName</th>
						<th scope="col">Age</th>
						<th scope="col">Email</th>
						<th scope="col">Occupation</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr>
							<td style="text-align: center;"><input type=checkbox
								name="usercheckbox" value="${user.id}" class="dltcheckbox"
								onchange="onDelete()"></td>
							<td style="text-align: center;">${user.id}</td>
							<td style="text-align: center;"><a
								href="/updateUser/${user.id}" title="Click to Update User">${user.firstName}</a></td>
							<td style="text-align: center;">${user.lastName}</td>
							<td style="text-align: center;">${user.age}</td>
							<td style="text-align: center;">${user.email}</td>
							<td style="text-align: center;">${user.occupation}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>