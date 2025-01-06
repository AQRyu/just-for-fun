// AuthRoute.js
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const AuthRoute = () => {
  const { isAuthenticated } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    // Store the attempted path for redirect after login
    localStorage.setItem("redirectPath", location.pathname); // Or sessionStorage
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default AuthRoute;
