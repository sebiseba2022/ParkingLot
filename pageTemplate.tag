<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" required="true"%>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>
<jsp:doBody/>
<script src="${pageContext.request.contextPath}/scripts/form-validation.js"></script>
</body>
</html>
