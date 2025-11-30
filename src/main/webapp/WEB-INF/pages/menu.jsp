<html>
<header data-bs-theme="dark">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Parking Lot</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/"))
eq '/about.jsp' ? ' active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/about.jsp">About</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="#">Link</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/Cars">Cars</a></li>
                    <li class="nav-item">
                        <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                    </li>
                </ul>a
               <ul class="navbar-nav">
                   <c:choose>
                       <c:when test="${pageContext.request.getSession(false) != null && pageContext.request.getSession(false).getAttribute('username') != null}">
                           <li class="nav-item">
                               <span class="navbar-text me-3">Welcome, ${pageContext.request.getSession(false).getAttribute('username')}</span>
                           </li>
                           <li class="nav-item">
                               <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
                           </li>
                       </c:when>
                       <c:otherwise>
                           <li class="nav-item">
                               <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                           </li>
                       </c:otherwise>
                   </c:choose>
               </ul>
            </div>
        </div>
    </nav>
</header>
</html>