"use client";

import React, {useState} from "react";
import {BookType} from "@/types/book-type";
import Image from "next/image";
import axios from "axios";
import toast from "react-hot-toast";
import {useAuth} from "../../context/auth-context";
import {Button} from "@/components/ui/button";
import {AlertModal} from "@/components/ui/alert-modal";
import {usePathname, useRouter} from 'next/navigation';
import {Plus} from "lucide-react";


export default function BookCard({book}: { book: BookType }) {

    console.log(book);
    const {token} = useAuth();
    const router = useRouter();
    const [open, setOpen] = useState(false);
    const [loading, setLoading] = useState(false);
    const pathname = usePathname();

    const borrowBook = async () => {
        try {
            setLoading(true);
            const response = await axios.post(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/books/borrow/${book.id}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (response.status === 200) {
                toast.success("Book was added to your borrowed list successfully.");
                setOpen(false);
            }
        } catch (e) {
            console.error("Error borrowing book", e);
            if (axios.isAxiosError(e) && e.response) {
                if (e.response.status === 403) {
                    toast.error("This book is not available for borrowing right now. Try again later.");
                } else {
                    toast.error(`Error: ${e.response.data.message || 'Unknown error occurred'}`);
                }
            } else {
                toast.error("Error borrowing book. Try again later.");
            }
        } finally {
            setLoading(false);
        }
    };

    const modifyArchivedStatus = async () => {
        //TODO:  Implement archive functionality
    };

    const modifyShareableStatus = async () => {
        // Implement share functionality
    };

    const editBook = async () => {
        // Implement edit functionality
    };

    const createBook = () => {
        // TODO: create schema and form validation separate component
        router.push('/add-book');
    }

    return (
        <div className={"flex flex-col"}>
            <Button
                onClick={createBook}
            >
                <Plus size={24} className={"mr-4"}/>
                Add Book
            </Button>
            {pathname === '/books' && (
                <AlertModal
                    isOpen={open}
                    onClose={() => setOpen(false)}
                    onConfirm={borrowBook}
                    loading={loading}
                    title={`Borrow ${book.title}`}
                />
            )}

            <div className="book-card">


                {book.coverImage && (
                    <Image src={"data:image/jpg;base64," + book.coverImage} alt={book.title + " cover image"} width={300} height={300}/>
                )}


                <h2>{book.title}</h2>
                <p>{book.authorName}</p>
                <p>{book.synopsis}</p>
                <p>ISBN: {book.isbn}</p>
                <p>Rating: {book.averageRating}</p>

                {pathname === '/books' && (
                    <Button
                        onClick={() => setOpen(true)}
                        disabled={loading}
                    >
                        Borrow
                    </Button>
                )}

                {pathname === '/my-books' && (
                    <>
                        <Button onClick={editBook} disabled={loading}>Update</Button>
                        <Button onClick={modifyArchivedStatus} disabled={loading}>Delete</Button>
                        <Button onClick={modifyShareableStatus} disabled={loading}>Share</Button>
                    </>
                )}
            </div>
        </div>
    );
}