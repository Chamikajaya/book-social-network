import {ReactNode} from "react"

import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import Header from "@/components/auth-components/header";
import BackBtn from "@/components/auth-components/back-btn";


interface CardWrapperProps {
    children: ReactNode;
    title: string;
    formTopic: string;
    backBtnLabel?: string;
    backBtnLink?: string;
}

export default function CardWrapper(
    {
        children,
        title,
        formTopic,
        backBtnLabel,
        backBtnLink,
    }: CardWrapperProps) {

    return (
        <Card className="w-[800px] shadow-lg shadow-primary">
            <CardHeader>
                <CardTitle>
                    <Header label={title} formTopic={formTopic}/>
                </CardTitle>
            </CardHeader>
            <CardContent className="flex flex-col gap-3">
                {children}
            </CardContent>
            <CardFooter>
                {backBtnLabel && backBtnLink && (
                    <BackBtn backBtnLabel={backBtnLabel} backBtnLink={backBtnLink}/>
                )}

            </CardFooter>
        </Card>

    )
}