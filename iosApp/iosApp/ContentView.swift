import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        let urlSession: URLSession = .init(configuration: .default)
        return MainViewControllerKt.MainViewController(
            nsurlSession: urlSession,
            hostApi: IosHostApi(),
            listener: DemoListener()
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

class DemoListener: MainEventListener {
    // private var success = true
    // private var snackBar: SnackBarPresentable? = nil

    func codeLoadFailed() {
        // if (success) {
        //     // Only show the Snackbar on the first transition from success.
        //     success = false
        //     let snackBar = SnackBar.make(in: view, message: "Unable to load guest code from server", duration: SnackBar.Duration.infinite)
        //         .setAction(with: "Dismiss", action: { self.maybeDismissSnackBar() })
        //     snackBar.show()
        //     self.snackBar = snackBar
        // }
    }

    func codeLoadSuccess() {
        // success = true
        // maybeDismissSnackBar()
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
