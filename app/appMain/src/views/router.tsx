import {BrowserRouter, Route, Routes} from "react-router-dom";
import type {ReactNode} from "react";
import {HomePage} from "@/views/pages/home";
import {CommandPage} from "@/views/pages/command";

/**
 * 路由列表
 *
 * TODO:根据实际需求增添其他功能，例如自动检测是否登录、IP封禁等
 *
 * @author lignting
 */
const routes: {
    path: string;
    element: ReactNode;
}[] = [
    {
        path: "/test",
        element: <div>Test Page</div>
    },
    {
        path: "/",
        element: <HomePage/>
    },
    {
        path: "/command",
        element: <CommandPage/>
    }
]

/**
 * 基础路由设置
 * @author lignting
 */
const AppRouter = () => {
    return <BrowserRouter>
        <Routes>
            {routes.map((item, index) => {
                return <Route key={index} path={item.path} element={item.element}/>
            })}
        </Routes>
    </BrowserRouter>
}

export {AppRouter}