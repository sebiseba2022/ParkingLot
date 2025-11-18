<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Cars">
    <body>
        <h1>Add car</h1>

            <template id="addCarTemplate">
                <section>
                    <h1>Add Car</h1>
                    <form method="post" action="${pageContext.request.contextPath}/AddCar">
                        <div>
                            <label for="license_plate">License plate</label>
                            <input type="text" id="license_plate" name="license_plate" required />
                        </div>

                        <div>
                            <label for="parking_spot">Parking spot</label>
                            <input type="text" id="parking_spot" name="parking_spot" required />
                        </div>

                        <div>
                            <label for="owner_id">Owner</label>
                            <select id="owner_id" name="owner_id" required>
                                <option value="">Choose...</option>
                            </select>
                        </div>

                        <div>
                            <button type="submit">Save</button>
                        </div>
                    </form>
                </section>
            </template>

    </body>
</t:pageTemplate>
