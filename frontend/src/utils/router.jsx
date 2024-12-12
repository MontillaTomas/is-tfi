import { lazy } from "react"
const RedirectIfLoggedIn = lazy(()=> import("../components/RedirectIfLoggedIn.jsx"))
const LoginPage = lazy(() => import("../pages/LoginPage.jsx"));
const MainPage = lazy(() => import("../pages/MainPage.jsx"));
const ProtectedRoutes = lazy(() => import("../components/ProtectedRoutes.jsx"));

export const routes = [
    {
        path:"/login",
        element: <RedirectIfLoggedIn><LoginPage/></RedirectIfLoggedIn>
    },
    {
        path:"/",
        element: <ProtectedRoutes element={<MainPage/>}/>
    }
];