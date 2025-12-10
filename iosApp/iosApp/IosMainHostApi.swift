//
// Created by adev on 10/12/2025.
//

import Foundation
import UIKit
import ComposeApp

class IosMainHostApi: MainHostApiService {
    private let baseUrl = IosBaseUrlKt.getBaseUrl()
    private let client: URLSession = .init(configuration: .default)

    func httpCall(url: String, headers: [String: String], completionHandler: @escaping (String?, Error?) -> Void) {
        let realUrl: String
        if (url.contains("://")) {
            realUrl = url
        } else {
            realUrl = "\(baseUrl)/\(url)"
        }
        var request = URLRequest(url: URL(string: realUrl)!)
        for (name, value) in headers {
            request.addValue(value, forHTTPHeaderField: name)
        }
        let task = client.dataTask(with: request) { data, response, error in
            // The KMM memory model doesn't do shared objects well, so Zipline expects the callback
            // on the same thread that the download was initiated from. This happens to be the main
            // thread, so we can bounce back to that thread for now.
            // Switching to the new KMM memory model may remove the need for this.
            DispatchQueue.main.async {
                completionHandler(data.map {
                    return String(decoding: $0, as: UTF8.self)
                }, error)
            }
        }

        task.resume()
    }

    func openUrl(url: String) {
        guard let url = URL(string: url) else {
            return
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url)
        }
    }

    func close() {
    }
}
