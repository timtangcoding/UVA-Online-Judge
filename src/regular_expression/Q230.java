/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=166
 * 230 - Borrowers
 */

package regular_expression;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Q230 {
	
	class BookShelf{
		private LinkedList<Book> books;
		
		public BookShelf(){
			this.books = new LinkedList<Book>();
		}
		
		public boolean addBook(Book book){
			boolean result = books.add(book);
			Collections.sort(books);
			return result;
		}
		
		public int getPositionOfBook(Book book){
			return Collections.binarySearch(books, book);
		}
		
		public Book getBookByPosition(int position){
			return books.get(position);
		}
		
		public void takeOutBook(Book desiredBook){
			int bookPosition = getPositionOfBook(desiredBook);
			if(bookPosition >= 0){
			 books.remove(bookPosition);
			}
		}
		
		public void checkBookList(){
			int bookSize = books.size();
			for(int i=0; i<bookSize; i++){
				System.out.println(books.get(i).getTitle() + " by " + books.get(i).getAuthor());
			}
		}
	}

	class Book implements Comparable<Book>{
		private String title;
		private String author;
		
		public Book(String author, String title){
			this.author = author;
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		@Override
		public int compareTo(Book arg0) {
			// TODO Auto-generated method stub
			int result = 0;
			if(this.author != null && arg0.author != null){
				result = this.author.compareTo(arg0.getAuthor());
			}
			
			if(result == 0){
				return this.title.compareTo(arg0.getTitle());
			}
			
			return result;
		}
	}
	
	static class Command{
		enum Action{
			BORROW(0), RETURN(1), SHELVE(2);
			private int value;
			Action(int v){
				this.value = v;
			}
		}
		
		private Action action;
		private String title;
		public Action getAction() {
			return action;
		}
		public void setAction(String action) {
			if(action.equals("BORROW"))
				this.action = Action.BORROW;
			else if(action.equals("RETURN"))
				this.action = Action.RETURN;
			else if(action.equals("SHELVE"))
				this.action = Action.SHELVE;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
	
	private BookShelf bookShelf;
	private HashMap<String, Book>bookIndex;
	private LinkedList<Book> booksReturned;
	
	//"The Canterbury Tales" by Chaucer, G.
	private Pattern contentInsideQuotes = Pattern.compile("(?<=^\")[\\D\\d]+(?=\")");
	private Pattern authorName = Pattern.compile("(?<=^\"[\\D\\d]{1,80}\"\\sby\\s)\\D+");
	private Pattern commandAction = Pattern.compile("^\\w+");
	private Pattern commandBookName = Pattern.compile("(?<=\\s\")[\\D\\d]+(?=\")");
	
	
	public Q230(){
		bookShelf = new BookShelf();
		bookIndex = new HashMap<String, Book>();
		booksReturned = new LinkedList<Book>();
		
	}
	
	public void handleCommand(Command command){
		switch(command.getAction()){
		case BORROW:
			bookShelf.takeOutBook(bookIndex.get(command.getTitle()));
			break;
		case RETURN:
			Book bookToReturn = bookIndex.get(command.getTitle());
			booksReturned.push(bookToReturn);
			break;
		case SHELVE:
			if(booksReturned.size() > 0)
				Collections.sort(booksReturned);
			while(!booksReturned.isEmpty()){
				Book returningBook = booksReturned.pop();
				bookShelf.addBook(returningBook);
				int position = bookShelf.getPositionOfBook(returningBook);
				if(position == 0){
					System.out.println("Put \"" + returningBook.getTitle() + "\" first");
				}else if(position > 0){
					Book previousBook = bookShelf.getBookByPosition(position-1);
					System.out.println("Put \""+ returningBook.getTitle() + "\" after \""+previousBook.getTitle()+"\"");
				}
			}
			System.out.println("END");
			break;
		}
	}
	
	public void readBookName(String inputStatment){
		Matcher bookNameMatcher = contentInsideQuotes.matcher(inputStatment);
		Matcher authorNameMatcher = authorName.matcher(inputStatment);
		String title = null;
		String author = null;
		if(bookNameMatcher.find()){
			title = bookNameMatcher.group(0);
		}
		
		if(authorNameMatcher.find()){
			author = authorNameMatcher.group(0);
		}
		Book newBook = new Book(author, title);
		bookIndex.put(title, newBook);
		bookShelf.addBook(newBook);
	}
	
	public Command readCommand(String input){
		Command command = new Command();
		Matcher commandActionMatcher = commandAction.matcher(input);
		Matcher commandBookNameMatcher = commandBookName.matcher(input);
		if(commandActionMatcher.find()){
			command.setAction(commandActionMatcher.group(0));
		}
		
		if(commandBookNameMatcher.find()){
			command.setTitle(commandBookNameMatcher.group(0));
		}
		return command;
	}
	
	public void checkBookList(){
		bookShelf.checkBookList();
	}
	
	
	
	static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        String line = "";

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input;
		int endCount = 0;
		Q230 solver = new Q230();
		boolean terminate = false;
		
		while((input = Q230.ReadLn(255)) != null){
			if(input.trim().equals("END")){
				endCount++;
			}else{
				switch(endCount){
				case 0:
					solver.readBookName(input);
					break;
				case 1:
					solver.handleCommand(solver.readCommand(input));
					break;
				case 2:
					
					terminate = true;
					break;
				}
			}
			if(terminate){
				break;
			}
			
		}

	}

}
