import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { isAuthenticated, handleLogout } = useAuth();

  return (
    <AppBar position="static">
      <Toolbar>
        <Box sx={{ flexGrow: 1, display: "flex", alignItems: "center" }}>
          {" "}
          {/* Added Box for alignment */}
          <Link to="/" style={{ textDecoration: "none" }}>
            <Typography variant="h6" component="div" sx={{ color: "#FFFFFF" }}>
              My Chat App
            </Typography>
          </Link>
        </Box>

        <Box sx={{ display: "flex", alignItems: "center" }}>
          {isAuthenticated ? (
            <Button sx={{ color: "#FFFFFF" }} onClick={handleLogout}>
              Logout
            </Button>
          ) : (
            <Link to="/login" style={{ textDecoration: "none" }}>
              <Button sx={{ color: "#FFFFFF" }}>Login</Button>
            </Link>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
