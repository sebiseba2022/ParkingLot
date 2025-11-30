<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit Car">
    <h1 class="text-center">Edit Car</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <div class="container">
        <form class="needs-validation" method="post" action="${pageContext.request.contextPath}/EditCar">
            <input type="hidden" name="car_id" value="${car.id}">

            <div class="mb-3">
                <label for="license_plate" class="form-label">License Plate</label>
                <input type="text" class="form-control" id="license_plate" name="license_plate" value="${car.licensePlate}" required>
                <div class="invalid-feedback">
                    Please provide a license plate.
                </div>
            </div>

            <div class="mb-3">
                <label for="parking_spot" class="form-label">Parking Spot</label>
                <input type="text" class="form-control" id="parking_spot" name="parking_spot" value="${car.parkingSpot}" required>
                <div class="invalid-feedback">
                    Please provide a parking spot number.
                </div>
            </div>

            <div class="mb-3">
                <label for="owner_id" class="form-label">Owner</label>
                <select class="form-select" id="owner_id" name="owner_id" required>
                    <option value="" disabled>Choose an owner...</option>
                    <c:forEach var="user" items="${users}">
                        <option value="${user.id}" ${user.username == car.ownerName ? 'selected' : ''}>${user.username}</option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">
                    Please select an owner.
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Update Car</button>
            <a href="${pageContext.request.contextPath}/Cars" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script>
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms)
                .forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
        })()
    </script>
</t:pageTemplate>
