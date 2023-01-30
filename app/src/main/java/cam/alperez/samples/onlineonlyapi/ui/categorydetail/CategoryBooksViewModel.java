package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.Collection;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;

public class CategoryBooksViewModel  extends AndroidViewModel {
    private final CategoryEntity category;


    //TODO Implement this
    public CategoryBooksViewModel(@NonNull Application app, @NonNull CategoryEntity category) {
        super(app);
        this.category = category;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setBooks(Collection<BookEntity> books) {
        //TODO Implement this
    }

    public void fetchBooks() {
        //TODO Implement this
    }


    /****************************  Factory for this ViewModel  ************************************/
    public static class Factory implements ViewModelProvider.Factory {

        private final CategoryEntity category;

        public Factory(CategoryEntity category) {
            this.category = category;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
            final Application app = extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
            if (app == null) {
                throw new RuntimeException("Application is null when ViewModel creation");
            }
            return (T) new CategoryBooksViewModel(app, category);
        }
    }
}
