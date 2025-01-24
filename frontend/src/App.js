import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import AuthRoute from "./components/AuthRoute";
import { AuthProvider } from "./context/AuthContext";
import ChatPage from "./pages/ChatPage";
import CreateWorkspacePage from "./pages/CreateWorkspacePage";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";
import WorkspaceManagementPage from "./pages/WorkspaceManagementPage";
import { Container } from "@mui/material";
import { StompProvider } from "./context/StompContext";

function App() {
  return (
    <AuthProvider>
      <StompProvider>
        <Router>
          <Container sx={{ height: "100vh", padding: 0, margin: 0 }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route element={<AuthRoute />}>
                <Route path="/chat" element={<ChatPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route
                  path="/workspace/create"
                  element={<CreateWorkspacePage />}
                />
                <Route
                  path="/workspace/:workspaceId/manage"
                  element={<WorkspaceManagementPage />}
                />
              </Route>
            </Routes>
          </Container>
        </Router>
      </StompProvider>
    </AuthProvider>
  );
}

export default App;
