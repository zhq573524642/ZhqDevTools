package com.zhq.devtools.ui.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zhq.devtools.App;
import com.zhq.devtools.ui.jetpack.db.AppDatabase;
import com.zhq.devtools.ui.jetpack.db.MenuDao;
import com.zhq.devtools.ui.jetpack.db.TangPoemDao;
import com.zhq.devtools.ui.jetpack.db.UserDao;
import com.zhq.devtools.ui.jetpack.entity.Menu;
import com.zhq.devtools.ui.jetpack.entity.TangPoem;
import com.zhq.devtools.ui.jetpack.entity.User;
import com.zhq.devtools.ui.jetpack.repository.MenuRepository;
import com.zhq.devtools.ui.jetpack.repository.TangPoemRepository;
import com.zhq.devtools.ui.jetpack.repository.UserRepository;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 11:07
 * Description
 */
public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
        private final TangPoemRepository tangPoemRepository;
    private  LiveData<List<TangPoem>> tangPoem;
    private LiveData<Menu> poemMenu;
    private final MenuRepository menuRepository;
    private LiveData<User> user;
    private static final String TAG = "UserViewModel";
    private String username = "zhq573524642";
    private String nickname="鱼死网破的呼吸";

    public UserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = App.getAppDatabase();

        UserDao userDao = appDatabase.userDao();
        userRepository = new UserRepository(userDao, App.getApiService());
        user = userRepository.getUser(username,nickname);

        TangPoemDao tangPoemDao = appDatabase.tangPoemDao();
        tangPoemRepository = new TangPoemRepository(tangPoemDao);
        tangPoem = tangPoemRepository.getTangPoem();

        MenuDao menuDao = appDatabase.menuDao();
        menuRepository = new MenuRepository(menuDao);
        poemMenu = menuRepository.getTangPoem(1);

    }

    public LiveData<User> getUser() {
        return user;
    }

    public void refresh() {
        userRepository.refresh(username);
    }

    public LiveData<List<TangPoem>> getTangPoem() {
        return tangPoem;
    }

    public LiveData<Menu> getPoemMenu() {
        return poemMenu;
    }
}
