import React, { useState } from "react";
import { BookType } from "@/types/book-type";
import Image from "next/image";
import axios from "axios";
import toast from "react-hot-toast";
import { useAuth } from "../../context/auth-context";
import { Button } from "@/components/ui/button";
import { AlertModal } from "@/components/ui/alert-modal";

export default function BookCard({ book }: { book: BookType }) {
    const { token } = useAuth();
    const [open, setOpen] = useState(false);
    const [loading, setLoading] = useState(false);

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

    return (
        <>
            <AlertModal
                title="Confirm Borrowing"
                description={`Are you sure you want to borrow "${book.title}"?`}
                isOpen={open}
                onClose={() => setOpen(false)}
                onConfirm={borrowBook}
                loading={loading}
            />

            <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                <Image
                    src={`data:image/jpg;base64,${book.coverImage}`}
                    alt={`Cover of ${book.title}`}
                    width={300}
                    height={300}
                />
                <div className="p-4">
                    <div className="flex justify-between items-center">
                        <h2 className="text-xl font-semibold">{book.title}</h2>
                        <span className="text-gray-500">{book.authorName}</span>
                    </div>
                    <div className="mt-2 text-gray-700">{book.synopsis}</div>
                    <div className="mt-4 text-sm text-gray-500">ISBN: {book.isbn}</div>
                    <div className="mt-2 flex justify-between items-center">
                        <span className="text-sm text-gray-500">Rating: {book.averageRating}</span>
                    </div>
                    <div className="flex justify-between">
                        <Button
                            onClick={() => setOpen(true)}
                            disabled={loading}
                        >
                            Borrow
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
}