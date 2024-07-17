"use client";

import CardWrapper from "@/components/auth-components/card-wrapper";
import {SignUpSchema, signUpSchema} from "@/schemas/authValidation";
import {zodResolver} from "@hookform/resolvers/zod";
import {useForm} from "react-hook-form";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {useState} from "react";
import axios from "axios";
import {Lock, Mail, User} from "lucide-react";
import FormError from "@/components/form-error";
import FormSuccess from "@/components/form-success";


export default function SignUpForm() {


    const [isSubmitting, setIsSubmitting] = useState(false)
    const [formErrMsg, setFormErrMsg] = useState("")
    const [formSuccessMsg, setFormSuccessMsg] = useState("")

    const form = useForm<SignUpSchema>({
        resolver: zodResolver(signUpSchema),
        defaultValues: {
            email: "",
            password: "",
            firstName: "",
            lastName: ""
        }
    })

    const onSubmit = async (formData: SignUpSchema) => {
        console.log("On submit was called")
        try {


            console.log(formData);

            setIsSubmitting(true)
            setFormErrMsg("")
            setFormSuccessMsg("")

            const response = await axios.post(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/auth/register`,
                {
                    email: formData.email,
                    password: formData.password,
                    firstName: formData.firstName,
                    lastName: formData.lastName
                },
                {
                    withCredentials: true
                }
            );
            console.log(response)

            if (response.status === 202) {
                setFormSuccessMsg("A confirmation email has been sent to your email account with OTP code .")
            }
        } catch (error) {
            console.error(error)
            // @ts-ignore
            if (error.response.status === 409) {
                setFormErrMsg("Email already exists.")
            } else {
                setFormErrMsg("Something went wrong");
            }
        } finally {
            setIsSubmitting(false)
        }


    }


    return (
        <CardWrapper
            title="First step in modernized book managing 📚"
            backBtnLabel="Already have an account ? "
            backBtnLink="/sign-in"
            formTopic={"Register 🔒"}
        >
            <Form {...form}>
                <form
                    onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                    <div className="space-y-4">
                        <FormField
                            control={form.control}
                            name="email"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel className={"font-normal flex gap-2"}>
                                        Email
                                        <Mail/>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={"email"}
                                            placeholder={"your email here"}
                                        />
                                    </FormControl>
                                    <FormMessage className={"text-red-400 font-normal"}/>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="firstName"
                            render={({field}) => (
                                <FormItem className={""}>
                                    <FormLabel className={"font-normal flex gap-2 items-center"}>
                                        First Name
                                        <User className={"h-4 w-4"}/>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={"text"}
                                            placeholder={"your first name here"}
                                        />
                                    </FormControl>
                                    <FormMessage className={"text-red-400 font-normal"}/>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="lastName"
                            render={({field}) => (
                                <FormItem className={""}>
                                    <FormLabel className={"font-normal flex gap-2 items-center"}>
                                        Last Name
                                        <User className={"h-4 w-4"}/>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={"text"}
                                            placeholder={"your last name here"}
                                        />
                                    </FormControl>
                                    <FormMessage className={"text-red-400 font-normal"}/>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="password"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel className={"font-normal flex gap-2"}>
                                        Password
                                        <Lock/>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={"password"}
                                            placeholder={"*****"}
                                        />
                                    </FormControl>
                                    <FormMessage className={"text-red-400 font-normal"}/>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="confirmPassword"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel className={"font-normal flex gap-2"}>
                                        Confirm Password
                                        <Lock/>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={"password"}
                                            placeholder={"*****"}
                                        />
                                    </FormControl>
                                    <FormMessage className={"text-red-400 font-normal"}/>
                                </FormItem>
                            )}
                        />
                    </div>
                    <FormError errMessage={formErrMsg}/>
                    <FormSuccess successMessage={formSuccessMsg}/>
                    <Button type="submit" className="w-full" size={"sm"} disabled={isSubmitting || !!formSuccessMsg}>
                        Create an account
                    </Button>

                </form>


            </Form>
        </CardWrapper>
    )
}