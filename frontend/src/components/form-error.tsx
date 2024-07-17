import { Frown } from 'lucide-react';

interface FormErrorProps {
    errMessage:string
}
export default function FormError({errMessage}:FormErrorProps) {
    if (!errMessage) return null;

    return (
        <div className={"bg-white/10 p-3 rounded-md flex items-center gap-x-2 text-sm text-red-600 font-medium justify-center"}>
            <Frown className={"h-4 w-4"}/>

            <p>{errMessage}</p>
        </div>
    )
}