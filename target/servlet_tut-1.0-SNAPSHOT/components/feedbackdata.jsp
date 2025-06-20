<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Feedback List</title>
    <%@include file="links.jsp" %>
    <style>
        body {
            background-color: #1e1f22;
        }
        .table {
            border-radius: 8px;
            overflow: hidden;
        }
        .feedback-message {
            max-width: 300px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .card {
            border: none;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <%@include file="headers.jsp" %>
    <div class="container mt-4">
        <div class="card" style="background-color: #2b2d30;">
            <div class="card-header p-4 text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h3 class="mb-0">Feedback Submissions</h3>
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                        <i class="bi bi-plus-lg"></i> New Feedback
                    </a>
                </div>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-dark table-hover mb-0">
                        <thead>
                            <tr>
                                <th scope="col">#ID</th>
                                <th scope="col">Email</th>
                                <th scope="col">Mobile</th>
                                <th scope="col">Feedback</th>
                                <th scope="col">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            try {
                                Connection conn = JDBC.DBConnection.getConnection();
                                String sql = "SELECT * FROM feedbackForm ORDER BY id ";
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery(sql);

                                while(rs.next()) {
                            %>
                            <tr>
                                <td><%= rs.getInt("id") %></td>
                                <td><%= rs.getString("email") %></td>
                                <td><%= rs.getString("mobile") %></td>
                                <td class="feedback-message" title="<%= rs.getString("feedback") %>">
                                    <%= rs.getString("feedback") %>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-info" title="View Details">
                                        <i class="bi bi-eye"></i>
                                    </button>
                                </td>
                            </tr>
                            <%
                                }
                                rs.close();
                                stmt.close();
                                conn.close();
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <%@include file="script.jsp" %>
    <script>
        // Initialize tooltips
        const tooltipTriggerList = document.querySelectorAll('[title]');
        const tooltipList = [...tooltipTriggerList].map(element => new bootstrap.Tooltip(element));
    </script>
</body>
</html>