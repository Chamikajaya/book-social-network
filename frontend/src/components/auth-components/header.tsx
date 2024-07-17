import {Montserrat} from "next/font/google"
import {cn} from "@/lib/utils";

const montserrat = Montserrat({
    weight: '600',
    subsets: ['latin']
})

interface HeaderProps {
    label: string;
    formTopic: string;
}

export default function Header({label, formTopic}: HeaderProps) {
    return (
        <div className="w-full flex flex-col gap-y-4 items-center">
            <h1 className={cn("text-2xl font-semibold", montserrat.className)}>
                {formTopic}
            </h1>
            <p className="text-muted-foreground text-sm">
                {label}
            </p>

        </div>
    )
}