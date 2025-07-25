import {createRoot} from 'react-dom/client'
import {AppRouter} from "./views/router.tsx";

createRoot(document.getElementById('root')!).render(<AppRouter/>)