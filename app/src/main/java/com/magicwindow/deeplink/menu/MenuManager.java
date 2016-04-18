package com.magicwindow.deeplink.menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.fragment.ContactUsFragment;
import com.magicwindow.deeplink.fragment.EBusinessFragment;
import com.magicwindow.deeplink.fragment.NewsFragment;
import com.magicwindow.deeplink.fragment.O2OFragment;
import com.magicwindow.deeplink.fragment.PictureFragment;
import com.magicwindow.deeplink.fragment.SettingsFragment;
import com.magicwindow.deeplink.fragment.TourFragment;
import com.magicwindow.deeplink.utils.EventBusManager;

import cn.salesuite.saf.eventbus.EventBus;

/**
 * Created by Tony Shen on 15/11/25.
 */
public class MenuManager {

    private static MenuManager instance = null;
    public MWApplication app;
    private FragmentManager fragmentManager;
    private MenuType curType;
    private EventBus eventBus;

    public MenuManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        curType = MenuType.DUMB;
        app = MWApplication.getInstance();
        eventBus = EventBusManager.getInstance();
        eventBus.register(this);
    }

    /**
     * 返回菜单的总数
     *
     * @return
     */
    public static int getMenuCount() {

        return MenuType.values() != null ? MenuType.values().length - 1 : 0;
    }

    public MenuType getCurType() {
        return curType;
    }

    private boolean show(MenuType type) {
        if (curType == type) {
            return true;
        } else {
            hide(curType);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(type.getTag());
        if (fragment == null) {
            fragment = add(type);
            if (fragment == null) {
                return false;
            }
        }

        fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        curType = type;
        return true;
    }

    public boolean showByPosition(int index) {
        return show(MenuType.getMenuTypeByIndex(index));
    }

    private Fragment add(MenuType type) {
        Fragment fragment = create(type);
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.content_frame, fragment, type.getTag()).commitAllowingStateLoss();
        }
        return fragment;
    }

    private Fragment create(MenuType type) {
        Fragment fragment = null;
        switch (type) {
            default:
            case HOME:
                fragment = new TourFragment();
                break;
            case O2O:
                fragment = new O2OFragment();
                break;
            case GALLERIES:
                fragment = new PictureFragment();
                break;
            case EBUSINESS:
                fragment = new EBusinessFragment();
                break;
            case NEWS:
                fragment = new NewsFragment();
                break;
            case SETTINGS:
                fragment = new SettingsFragment();
                break;
            case CONTACT:
                fragment = new ContactUsFragment();
                break;
        }
        return fragment;
    }

    private void hide(MenuType type) {
        Fragment fragment = fragmentManager.findFragmentByTag(type.getTag());
        if (fragment != null) {
            if (type.hasRemoved()) {
                fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
            } else {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                // ft.addToBackStack(type.getTitle());
                ft.hide(fragment);
                ft.commit();
            }
        }
    }

    /**
     * 判断某个fragment是否存在
     *
     * @param type
     * @return
     */
    public boolean isFragmentExist(MenuType type) {
        Fragment fragment = fragmentManager.findFragmentByTag(type.getTag());
        return fragment != null;

    }

    public Fragment getCurrentFragment() {
        return fragmentManager.findFragmentByTag(curType.getTag());

    }

    public enum MenuType {

        HOME(0, R.string.menu_text_01, R.drawable.menu_icon_01, false),
        EBUSINESS(1, R.string.menu_text_04, R.drawable.menu_icon_04, true),
        O2O(2, R.string.menu_text_02, R.drawable.menu_icon_02, true),
        GALLERIES(3, R.string.menu_text_03, R.drawable.menu_icon_03, true),
        NEWS(4, R.string.menu_text_05, R.drawable.menu_icon_05, true),
        SETTINGS(5, R.string.menu_text_06, R.drawable.menu_icon_06, true),
        CONTACT(6, R.string.menu_text_07, R.drawable.menu_icon_07, true),
        //        SIGNOUT(6, R.string.menu_text_07, R.drawable.menu_icon_07, true),
        DUMB(-1, R.string.menu_text_01, R.drawable.menu_icon_01, true);

        public final int position;
        private final int title;
        private final int icon;
        private final boolean removed; // 表示fragment是否需要remove

        MenuType(int position, final int title, int icon, boolean removed) {
            this.position = position;
            this.title = title;
            this.icon = icon;
            this.removed = removed;
        }

        public static MenuType getMenuTypeByIndex(int index) {
            switch (index) {
                default:
                case 0:
                    return HOME;
                case 1:
                    return EBUSINESS;
                case 2:
                    return O2O;
                case 3:
                    return GALLERIES;
                case 4:
                    return NEWS;
                case 5:
                    return SETTINGS;
                case 6:
                    return CONTACT;
            }
        }

        public static int getIconByIndex(int index) {
            return getMenuTypeByIndex(index).icon;
        }

        public static int getTitleByIndex(int index) {
            return getMenuTypeByIndex(index).title;
        }

        public String getTag() {
            return String.valueOf(title);
        }

        public int getTitleRes() {
            return title;
        }

        public boolean hasRemoved() {
            return removed;
        }
    }
}
