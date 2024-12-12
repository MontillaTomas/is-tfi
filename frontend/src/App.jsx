import React from 'react'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { AuthContext } from './context/AuthContext'
import { routes } from './utils/router';

const createRoutes = () => {
  return routes.map((route) => {
    return route;
  });
};

function App() {
  const router = createBrowserRouter([...routes]);
  return (
    <AuthContext>
      <RouterProvider router={router}/>
    </AuthContext>
  )
}

export default App

