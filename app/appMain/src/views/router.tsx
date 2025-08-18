import {BrowserRouter, Route, Routes} from "react-router-dom";
import type {ReactNode} from "react";
import {HomePage} from "@/views/pages/home";
import {CommandPage} from "@/views/pages/command";
import Test from "@/views/pages/test";
import {blogRoutes} from "@/views/pages/blog/router.tsx";

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
        path: "/",
        element: <HomePage/>
    },
    {
        path: "/command",
        element: <CommandPage/>
    },
    {
        path: "/test",
        element: <Test/>
    }
]

const childRoutes: {
    name: string,
    routes: {
        path: string;
        element: ReactNode;
    }[]
}[] = [
    {
        name: "blog",
        routes: blogRoutes
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
            {childRoutes.map((item, index) => {
                return item.routes.map((childItem, childIndex) => {
                    return <Route
                        key={`${index}-${childIndex}`}
                        path={`/${item.name}${childItem.path}`}
                        element={childItem.element}
                    />
                })
            })}
        </Routes>
    </BrowserRouter>
}

export {AppRouter}