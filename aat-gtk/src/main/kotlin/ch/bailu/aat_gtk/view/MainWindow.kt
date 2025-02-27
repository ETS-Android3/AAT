package ch.bailu.aat_gtk.view

import ch.bailu.aat_gtk.app.App
import ch.bailu.aat_gtk.app.GtkAppConfig
import ch.bailu.aat_gtk.app.GtkAppContext
import ch.bailu.aat_gtk.util.GtkTimer
import ch.bailu.aat_gtk.util.IconMap
import ch.bailu.aat_gtk.view.menu.provider.AppMenu
import ch.bailu.aat_lib.dispatcher.*
import ch.bailu.aat_lib.gpx.InfoID
import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str

class MainWindow(window: ApplicationWindow, app: Application)
{

    private val trackerButton = TrackerButton(GtkAppContext.services)

    private val dispatcher = Dispatcher()

    private val contextRevealButton = ToggleButton()
    private val mainView = MainStackView(app, dispatcher,window, GtkAppContext.storage, contextRevealButton)
    private val contextBar = ContextBar(mainView,GtkAppContext.storage)

    private val box = Box(Orientation.VERTICAL, 0)


    init {
        box.append(contextBar.revealer)
        box.append(mainView.layout)

        window.child = box

        window.title = Str(GtkAppConfig.title)
        window.titlebar = createHeader(window, app, mainView)

        window.setDefaultSize(720 / 2, 1440 / 2)
        window.onDestroy {
            App.exit(0)
        }
        window.show()

        dispatcher.addSource(TrackerTimerSource(GtkAppContext.services, GtkTimer()))
        dispatcher.addSource(CurrentLocationSource(GtkAppContext.services, GtkAppContext.broadcaster))
        dispatcher.addSource(TrackerSource(GtkAppContext.services, GtkAppContext.broadcaster))
        dispatcher.addSource(OverlaySource(GtkAppContext))

        dispatcher.addTarget(trackerButton, InfoID.ALL)
        dispatcher.addTarget(contextBar, InfoID.ALL)

        dispatcher.onResume()
    }


    private fun createHeader(window: ApplicationWindow, app: Application, stack: MainStackView): HeaderBar {
        val header = HeaderBar()

        header.showTitleButtons = GTK.TRUE

        contextRevealButton.child = IconMap.getImage("zoom-original", 24)
        contextRevealButton.onToggled {
            contextBar.revealer.revealChild = contextRevealButton.active
        }

        header.packStart(MenuButton().apply {
            val appMenu = AppMenu(window, stack)
            menuModel = appMenu.createMenu().create(app)
            PopoverMenu(popover.cast()).apply {
                appMenu.createCustomWidgets().forEach {
                    addChild(it.widget, Str(it.id))
                }
            }
        })

        header.packStart(trackerButton.button)

        header.packEnd(contextRevealButton)

        return header
    }
}
