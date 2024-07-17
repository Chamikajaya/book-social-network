import {Laugh} from 'lucide-react';

interface FormSuccessProps {
    successMessage: string
}

export default function FormSuccess({successMessage}: FormSuccessProps) {
    if (!successMessage) return null;

    return (
        <div className={"bg-emerald-500/20 p-3 rounded-md flex items-center gap-x-2 text-sm text-emerald-500 font-medium justify-center"}>
            <Laugh className={"h-4 w-4"}/>
            <p>{successMessage}</p>
        </div>
    )
}