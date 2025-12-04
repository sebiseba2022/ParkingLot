<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Cars">

<h1>Cars</h1>
<%-- <c:if test="${hasWritePermission}"> --%>
    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddCar">Add cars</a>
<%-- </c:if> --%>

<div class="container mt-4">
    <form method="post" action="${pageContext.request.contextPath}/Cars">
        <div class="row fw-bold mb-3">
    <%--  <c:if test="${hasWritePermission}"> --%>
                <div class="col-1">Select</div>
            <%--  </c:if> --%>
            <div class="col">License Plate</div>
            <div class="col">Parking Spot</div>
            <div class="col">Owner</div>
            <div class="col">Actions</div>
        </div>
        <c:forEach var="car" items="${cars}">
            <div class="row mb-2 align-items-center">
            <%--   <c:if test="${hasWritePermission}"> --%>
                    <div class="col-1">
                        <input type="checkbox" name="car_ids" value="${car.id}" class="form-check-input">
                    </div>
                    <%--   </c:if> --%>
                <div class="col">
                    ${car.licensePlate}
                </div>
                <div class="col">
                    ${car.parkingSpot}
                </div>
                <div class="col">
                    ${car.ownerName}
                </div>
                <div class="col">
            <%--    <c:if test="${hasWritePermission}"> --%>
                        <a href="${pageContext.request.contextPath}/EditCar?id=${car.id}" class="btn btn-secondary btn-sm">Edit Car</a>
                    <%--  </c:if> --%>
                </div>
            </div>
        </c:forEach>
    <%--   <c:if test="${hasWritePermission}"> --%>
            <button type="submit" class="btn btn-danger mt-3">Delete Selected Cars</button>
            <%--   </c:if> --%>
    </form>
</div>

<h5 class="mt-4">Free parking spots: ${numberOfFreeParkingSpots}</h5>

</t:pageTemplate>
